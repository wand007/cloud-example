package com.cloud.example.jpa.strategy;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 咚咚锵
 * @date 2021/11/29 上午10:35
 * @description 用户检查上下文
 */
@Slf4j
@Setter
@Component
public abstract class UserCheckContext {
    @Resource
    private Map<String, UserCheckStrategy> strategyMap = new ConcurrentHashMap<>(8);

    public UserCheckStrategy getContext(String strategy) {
        return strategyMap.get(strategy);
    }
}
