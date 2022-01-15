package com.cloud.example;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */
@EnableAsync
@SpringBootApplication
public class ExampleToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleToolsApplication.class, args);
    }

    @Bean
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor asyncTaskThreadPool = new ThreadPoolTaskExecutor();
        asyncTaskThreadPool.setCorePoolSize(20);
        asyncTaskThreadPool.setMaxPoolSize(200);
        asyncTaskThreadPool.setQueueCapacity(11);
        asyncTaskThreadPool.setKeepAliveSeconds(30);
        asyncTaskThreadPool.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("Async-example-tools-%d").build());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        asyncTaskThreadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        asyncTaskThreadPool.initialize();
        return asyncTaskThreadPool;
    }

}
