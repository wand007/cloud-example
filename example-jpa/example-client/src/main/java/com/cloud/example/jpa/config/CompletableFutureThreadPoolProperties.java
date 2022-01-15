package com.cloud.example.jpa.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * @author 咚咚锵
 * @date 2021/6/24 下午3:27
 * @description 异步线程池配置参数
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "jpa.completable-future")
public class CompletableFutureThreadPoolProperties {

    @NestedConfigurationProperty
    private ThreadPool threadPool;

    @Getter
    @Setter
    @ToString
    public static class ThreadPool {
        /**
         * 核心线程数
         */
        private Integer corePoolSize;
        /**
         * 线程池中允许的最大线程数
         */
        private Integer maximumPoolSize;
        /**
         * 线程空闲时间
         */
        private Integer keepAliveTime;
        /**
         * 任务队列容量
         */
        private Integer queueCapacity;
    }
}
