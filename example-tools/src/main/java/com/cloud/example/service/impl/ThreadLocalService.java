package com.cloud.example.service.impl;

import com.cloud.example.service.IThreadLocalService;
import com.cloud.example.thread.ExecutorProcessPool;
import com.cloud.example.vo.BatchDetailsParam;
import com.cloud.example.vo.BatchParam;
import com.google.common.util.concurrent.AtomicDouble;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author 咚咚锵
 * @date 2021/4/3 18:44
 * @description ThreadLocal测试
 */
@Slf4j
@Service
public class ThreadLocalService implements IThreadLocalService {

    volatile BigDecimal amount = new BigDecimal(0);
    private static ThreadLocal<BatchParam> threadLocalParam = new ThreadLocal<>();

    /**
     * 设置参数
     *
     * @param sum
     */

    @Override
    public void initParam(int sum) {
        Thread t = Thread.currentThread();
        log.info("initParam------{}", t.getName());

        List<BatchDetailsParam> batchDetailsParams = new ArrayList<>();
        for (int i = 0; i < sum; i++) {
            BatchDetailsParam detailsParam = new BatchDetailsParam();
            detailsParam.setAmount(new BigDecimal("14"));
            batchDetailsParams.add(detailsParam);
        }
        BatchParam param = new BatchParam();
        param.setBatchName("ThreadLocal名称");
        param.setAmountTotal(BigDecimal.ZERO);
        param.setBatchDetailsParams(batchDetailsParams);

        threadLocalParam.set(param);
    }

    /**
     * 计算
     */
    @Override
    public void compute() {
        Thread t = Thread.currentThread();
        log.info("compute------{}", t.getName());

        ExecutorProcessPool pool = ExecutorProcessPool.getInstance();

        BatchParam batchParam = threadLocalParam.get();

        List<BatchDetailsParam> detailsParams = batchParam.getBatchDetailsParams();
        AtomicDouble atomicDouble = new AtomicDouble();

        CountDownLatch countDownLatch = new CountDownLatch(detailsParams.size());

        for (BatchDetailsParam detailsParam : detailsParams) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    Thread t = Thread.currentThread();
                    log.info("compute11111111------{}", t.getName());
                    log.info("addAndGet------{}", atomicDouble.addAndGet(detailsParam.getAmount().doubleValue()));

                    batchParam.setAmountTotal(batchParam.getAmountTotal().add(detailsParam.getAmount()));
                    amount = amount.add(detailsParam.getAmount());
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.info("countDownLatch------", e);
        }
        log.info("atomicDouble------{}", atomicDouble.doubleValue());
        log.info("amount------{}", amount.doubleValue());
    }

    @Override
    public BatchParam summaryResults() {
        Thread t = Thread.currentThread();
        log.info("summaryResults------{}", t.getName());
        BatchParam batchParam = threadLocalParam.get();
        return batchParam;
    }
}
