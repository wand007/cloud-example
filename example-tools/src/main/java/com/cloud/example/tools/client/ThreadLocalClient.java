package com.cloud.example.tools.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.thread.ExecutorProcessPool;
import com.cloud.example.tools.service.IThreadLocalService;
import com.cloud.example.tools.vo.BatchParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author 咚咚锵
 * @date 2021/4/3 18:39
 * @description ThreadLocal测试方法
 * <p>
 * <p>
 * 结果证明每次的请求都是在spring设置的线程池中获取新的线程，
 * 所以用ThreadLocal做参数传递的时候，
 * 会发生参数覆盖的情况，这种情况下的数据是不安全的，
 * 所以此种方案不可取，这种情况下内存泄露的的只是spring中线程池最大线程数量，不会无限泄露导致OOM
 */
@Slf4j
@RestController
@RequestMapping(value = "/threadLocal")
public class ThreadLocalClient extends BaseClient {


    @Resource
    private IThreadLocalService iThreadLocalService;

    @PostMapping(value = "/test2")
    public ResultResponse test2(@RequestParam(name = "sum", defaultValue = "1") Integer sum) throws InterruptedException {
        Thread t = Thread.currentThread();
        log.info("test2------{}", t.getName());
        ExecutorProcessPool pool = ExecutorProcessPool.getInstance();
        List<BatchParam> list = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(sum);
        for (int i = 0; i < sum; i++) {
            pool.execute(new Runnable() {
                @Override
                public synchronized void run() {
                    Thread t = Thread.currentThread();
                    log.info("test2---2222------{}", t.getName());

                    iThreadLocalService.initParam(sum);
                    iThreadLocalService.compute();
                    BatchParam batchParam = iThreadLocalService.summaryResults();
                    list.add(batchParam);
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.info("countDownLatch------", e);
        }

        return ResultResponse.success(list);
    }

}
