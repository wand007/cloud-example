package com.cloud.example.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author ; lidongdong
 * @Description 自定义session
 * @Date 2019-04-17
 * 使用springboot-session处理，单位：秒；
 * RedisFlushMode有两个参数：ON_SAVE（表示在response commit前刷新缓存），IMMEDIATE（表示只要有更新，就刷新缓存）
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800, redisFlushMode = RedisFlushMode.ON_SAVE, redisNamespace = "example_namespace")
public class HttpSessionConfig {

    /**
     * 配置Spring-Session共享
     */
    @Bean
    public CookieSerializer defaultCookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setCookieName("MyESSION");
        defaultCookieSerializer.setDomainName("127.0.0.1");
        defaultCookieSerializer.setCookiePath("/");
        return defaultCookieSerializer;
    }

//    @Bean
//    public CookieHttpSessionStrategy cookieHttpSessionStrategy(CookieSerializer defaultCookieSerializer){
//        CookieHttpSessionStrategy cookieHttpSessionStrategy = new CookieHttpSessionStrategy();
//        cookieHttpSessionStrategy.setCookieSerializer(defaultCookieSerializer);
//        return cookieHttpSessionStrategy;
//    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }


}
