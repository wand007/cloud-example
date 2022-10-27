package com.cloud.example.tools.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 咚咚锵
 * @date 2021/6/24 下午3:14
 * @description 线程池配置类
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {


    /**
     * Async 异步自定义线程池
     *
     * @return
     */
    @Bean("asyncExecutor")
    public ThreadPoolExecutor asyncExecutor() {

//        return (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        return new ThreadPoolExecutor(5, 5, 3000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(900));

//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
//                .setNameFormat("recognizeBasicThreadPool-pool-%d").build();
//
//        return new ThreadPoolExecutor(6, 6, 3000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(5000), namedThreadFactory);

//        ThreadPoolTaskExecutor asyncTaskThreadPool = new ThreadPoolTaskExecutor();
//        asyncTaskThreadPool.setCorePoolSize(4);
//        asyncTaskThreadPool.setMaxPoolSize(9);
//        asyncTaskThreadPool.setQueueCapacity(10);
//        asyncTaskThreadPool.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("AsyncPool-%d").build());
//        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
//        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
//        asyncTaskThreadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//
//        asyncTaskThreadPool.initialize();
//        return asyncTaskThreadPool;
    }

    /**
     * CompletableFuture 异步自定义线程池
     *
     * @return
     */
    @Bean("CompletableFutureExecutor")
    public AsyncTaskExecutor doctorGroupExecutor() {

        ThreadPoolTaskExecutor asyncTaskThreadPool = new ThreadPoolTaskExecutor();
        asyncTaskThreadPool.setCorePoolSize(4);
        asyncTaskThreadPool.setMaxPoolSize(9);
        asyncTaskThreadPool.setQueueCapacity(10);
        asyncTaskThreadPool.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("CompletableFuturePool-%d").build());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        asyncTaskThreadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        asyncTaskThreadPool.initialize();
        return asyncTaskThreadPool;
    }


}
