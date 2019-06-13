package com.cloud.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */
@EnableAsync
@EnableEurekaClient
@SpringBootApplication
@ServletComponentScan
//@EnableJpaRepositories("com.cloud.example.dao")
//@EntityScan("com.cloud.example.domain")
//@EnableMongoRepositories(basePackages = {"com.cloud.example.dao"})
public class ExampleSsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSsoServerApplication.class, args);
    }

    @Bean
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor asyncTaskThreadPool = new ThreadPoolTaskExecutor();
        asyncTaskThreadPool.setCorePoolSize(20);
        asyncTaskThreadPool.setMaxPoolSize(200);
        asyncTaskThreadPool.setQueueCapacity(11);
        asyncTaskThreadPool.setKeepAliveSeconds(30);
        asyncTaskThreadPool.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("Async-sso-example-%d").build());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        asyncTaskThreadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        asyncTaskThreadPool.initialize();
        return asyncTaskThreadPool;
    }


    /**
     * redisTemplate
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
