package com.cloud.example.jpa.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 咚咚锵
 * @date 2021/6/24 下午3:14
 * @description 线程池配置类
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {


    /**
     * 线程池名前缀
     */
    private static final String THREAD_NAME_PREFIX = "example-jpa-";

    /**
     * Async 异步自定义线程池
     *
     * @return
     */
    @Bean("asyncExecutor")
    public AsyncTaskExecutor asyncExecutor(AsyncThreadPoolProperties asyncConfigProperties) {

        AsyncThreadPoolProperties.ThreadPool threadPool = asyncConfigProperties.getThreadPool();

        ThreadPoolTaskExecutor asyncTaskThreadPool = new ThreadPoolTaskExecutor();
        asyncTaskThreadPool.setCorePoolSize(threadPool.getCorePoolSize());
        asyncTaskThreadPool.setMaxPoolSize(threadPool.getMaximumPoolSize());
        asyncTaskThreadPool.setQueueCapacity(threadPool.getKeepAliveTime());
        asyncTaskThreadPool.setKeepAliveSeconds(threadPool.getQueueCapacity());
        asyncTaskThreadPool.setThreadFactory(new ThreadFactoryBuilder().setNameFormat(THREAD_NAME_PREFIX + "AsyncPool-%d").build());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        asyncTaskThreadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        asyncTaskThreadPool.initialize();
        return asyncTaskThreadPool;
    }

    /**
     * completableFuture 异步自定义线程池
     *
     * @param completableFutureThreadPoolProperties
     * @return
     */
    @Bean("completableFutureExecutor")
    public ThreadPoolTaskExecutor completableFutureExecutor(CompletableFutureThreadPoolProperties completableFutureThreadPoolProperties) {
        CompletableFutureThreadPoolProperties.ThreadPool threadPool = completableFutureThreadPoolProperties.getThreadPool();

        ThreadPoolTaskExecutor asyncTaskThreadPool = new ThreadPoolTaskExecutor();
        asyncTaskThreadPool.setCorePoolSize(threadPool.getCorePoolSize());
        asyncTaskThreadPool.setMaxPoolSize(threadPool.getMaximumPoolSize());
        asyncTaskThreadPool.setQueueCapacity(threadPool.getKeepAliveTime());
        asyncTaskThreadPool.setKeepAliveSeconds(threadPool.getQueueCapacity());
        asyncTaskThreadPool.setThreadFactory(new ThreadFactoryBuilder().setNameFormat(THREAD_NAME_PREFIX + "CompletableFuturePool-%d").build());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        asyncTaskThreadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        asyncTaskThreadPool.initialize();
        return asyncTaskThreadPool;
    }

}
