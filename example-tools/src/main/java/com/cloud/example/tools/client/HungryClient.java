package com.cloud.example.tools.client;

import com.cloud.example.base.ResultResponse;
import com.cloud.example.tools.service.IAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 咚咚锵
 * @date 2022/10/24 下午8:00
 * @description 定长线程池饥饿示例
 */
@Slf4j
@RestController
@RequestMapping(value = "/hungry")
public class HungryClient {

    @Resource(name = "asyncExecutor")
    protected ThreadPoolExecutor threadPoolExecutor;

    @Resource
    protected IAsyncService iAsyncService;


    @ResponseBody
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public ResultResponse test1(@RequestParam(value = "num") Integer num) throws ExecutionException, InterruptedException {
        log.info("test1---------1");

        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int finalI = i;
            log.info("test1---------222222222");
            log.info("activeCount:" + threadPoolExecutor.getActiveCount());
            log.info("poolSize:" + threadPoolExecutor.getPoolSize());
            log.info("largestPoolSize:" + threadPoolExecutor.getLargestPoolSize());
            log.info("TaskCount:" + threadPoolExecutor.getTaskCount());
            log.info("CompletedTaskCount:" + threadPoolExecutor.getCompletedTaskCount());
            log.info("CorePoolSize:" + threadPoolExecutor.getCorePoolSize());
            log.info("LargestPoolSize:" + threadPoolExecutor.getLargestPoolSize());
            log.info("MaximumPoolSize:" + threadPoolExecutor.getMaximumPoolSize());
            log.info("ActiveThreadCount:" + threadPoolExecutor.getActiveCount());
            log.info("currentNumberOfThreads:" + threadPoolExecutor.getPoolSize());
            log.info("QueueSize:" + threadPoolExecutor.getQueue().size());
            log.info("test1---------3333333333 i={}", i);
            Future<String> mapFuture = threadPoolExecutor.submit(() -> iAsyncService.asyncHungry(finalI));
            futures.add(mapFuture);
        }
        log.info("test1---------4");
        List<String> stringList = new ArrayList<>();
        for (Future<String> future : futures) {
            stringList.add(future.get());
        }
        return ResultResponse.success(stringList);
    }

    @ResponseBody
    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public ResultResponse test2(@RequestParam(value = "num") Integer num) throws ExecutionException, InterruptedException {
        log.info("test2---------1");

        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int finalI = i;
            Future<String> mapFuture = threadPoolExecutor.submit(() -> iAsyncService.asyncHungry(finalI));
            futures.add(mapFuture);
        }
        log.info("test2---------4");
        List<String> stringList = new ArrayList<>();
        for (Future<String> future : futures) {
            stringList.add(future.get());
        }
        return ResultResponse.success(stringList);
    }

    @ResponseBody
    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    public ResultResponse test3(@RequestParam(value = "num") Integer num) throws ExecutionException, InterruptedException {
        log.info("test3---------1");

        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int finalI = i;
            Future<String> mapFuture = threadPoolExecutor.submit(() -> iAsyncService.asyncHungry(finalI));
            futures.add(mapFuture);
        }
        log.info("test3---------4");
        List<String> stringList = new ArrayList<>();
        for (Future<String> future : futures) {
            stringList.add(future.get());
        }
        return ResultResponse.success(stringList);
    }


}
