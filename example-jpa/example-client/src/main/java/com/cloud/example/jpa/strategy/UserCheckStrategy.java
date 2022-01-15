package com.cloud.example.jpa.strategy;

import com.cloud.example.jpa.exception.JpaException;
import com.cloud.example.jpa.param.req.OrderCreateReq;
import com.cloud.example.jpa.service.ResourceService;
import com.cloud.example.jpa.service.UserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 咚咚锵
 * @date 2021/11/29 上午10:35
 * @description 用户检查基类
 */
@Slf4j
@Setter
@Component
public abstract class UserCheckStrategy {

    @Resource(name = "completableFutureExecutor")
    protected ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    protected UserService userService;
    @Resource
    protected ResourceService resourceService;

    public void check(OrderCreateReq req) {
        this.checkRisk(req);
        this.checkUser(req.getUserId());
        this.checkResource(req.getResourceId());
    }

    /**
     * 检查用户
     *
     * @param userId
     */
    public void checkUser(String userId) {
        userService.findById(userId).orElseThrow(() -> new JpaException(1, "用户不存在"));
    }

    /**
     * 检查资源
     *
     * @param resourceId
     */
    public void checkResource(String resourceId) {
        resourceService.findById(resourceId).orElseThrow(() -> new JpaException(1, "资源不存在"));
    }

    /**
     * 检查风险
     *
     * @param req
     */
    public abstract void checkRisk(OrderCreateReq req);

}
