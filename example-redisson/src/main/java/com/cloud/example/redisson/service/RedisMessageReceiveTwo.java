package com.cloud.example.redisson.service;

import com.cloud.example.redisson.config.RedisMQConfig;
import com.cloud.example.redisson.vo.UserParam;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * @author ; lidongdong
 * @Description redis 消息处理器
 * @Date 2019-08-29
 */
@Slf4j
@Component
public class RedisMessageReceiveTwo {


    /**
     * 接收消息的方法
     */
    public void getMessage(String message, String channel) {
        log.info(channel + " 收到消息: " + message);

        switch (channel) {
            case RedisMQConfig.ADD_CHANNEL_KEY: {
                log.info(channel + " 收到消息: " + message);
                break;
            }
            case RedisMQConfig.UPDATE_DETAIL_CHANNEL_KEY: {
                log.info(channel + " 收到消息: " + message);
                break;
            }
            case RedisMQConfig.UPDATE_PRICE_CHANNEL_KEY: {
                //序列化对象（特别注意：发布的时候需要设置序列化；订阅方也需要设置序列化）
                Jackson2JsonRedisSerializer seria = new Jackson2JsonRedisSerializer(Object.class);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
                objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
                //对象的所有字段全部列入
                objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
                //取消默认转换timestamps形式
                objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
                objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                //忽略空Bean转json的错误
                objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
                seria.setObjectMapper(objectMapper);

                UserParam param = (UserParam) seria.deserialize(message.getBytes());
                log.info(channel + " 收到消息: " + param.toString());
                break;
            }
            default: {
                log.info("DEFAULT" + " 收到消息: " + message);
            }
        }
    }
}
