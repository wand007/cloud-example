package com.cloud.example.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.config.RedisMQConfig;
import com.cloud.example.vo.UserParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ; lidongdong
 * @Description redis消息订阅发布
 * @Date 2019-08-29
 */
@Slf4j
@RestController
@RequestMapping(value = "redismq")
public class RedisMQController extends BaseClient {

    @Autowired
    public RedisTemplate redisTemplate;

    @RequestMapping(value = "/sendMessage")
    @ResponseBody
    public ResultResponse sendMessage() {
        UserParam userParam = new UserParam();
        userParam.setAvatarUrl("https://www.baidu.com/");
        userParam.setId("616302922149752832");
        userParam.setNickName("尼古拉斯钢蛋");
        redisTemplate.convertAndSend(RedisMQConfig.ADD_CHANNEL_KEY, 1234567890);
        redisTemplate.convertAndSend(RedisMQConfig.UPDATE_DETAIL_CHANNEL_KEY, "hello word Java");
        redisTemplate.convertAndSend(RedisMQConfig.UPDATE_PRICE_CHANNEL_KEY, userParam);
        return ResultResponse.success();

    }
}
