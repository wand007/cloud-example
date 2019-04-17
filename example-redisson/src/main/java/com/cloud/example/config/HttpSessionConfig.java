package com.cloud.example.config;

import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author ; lidongdong
 * @Description 自定义session
 * @Date 2019-04-17
 * 使用springboot-session处理，单位：秒；
 * RedisFlushMode有两个参数：ON_SAVE（表示在response commit前刷新缓存），IMMEDIATE（表示只要有更新，就刷新缓存）
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800, redisFlushMode = RedisFlushMode.ON_SAVE, redisNamespace = "example_namespace")
public class HttpSessionConfig {

}
