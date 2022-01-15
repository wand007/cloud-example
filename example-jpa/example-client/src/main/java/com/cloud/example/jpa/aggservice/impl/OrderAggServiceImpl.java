package com.cloud.example.jpa.aggservice.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.example.jpa.aggservice.OrderAggService;
import com.cloud.example.jpa.exception.JpaException;
import com.cloud.example.jpa.jpa.domain.OrderDAO;
import com.cloud.example.jpa.jpa.domain.ResourceDAO;
import com.cloud.example.jpa.param.req.OrderCreateReq;
import com.cloud.example.jpa.param.rsp.OrderCreateRsp;
import com.cloud.example.jpa.service.OrderService;
import com.cloud.example.jpa.service.ResourceService;
import com.cloud.example.utils.SnowflakeIdWorker;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @author 咚咚锵
 * @date 2022/1/15 下午1:39
 * @description TODO
 */
@Slf4j
@Setter
@Service
public class OrderAggServiceImpl implements OrderAggService {

    @Resource(name = "completableFutureExecutor")
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    SnowflakeIdWorker snowflakeIdWorker;
    @Resource
    OrderService orderService;
    @Resource
    ResourceService resourceService;

    @Override
    public OrderDAO save(OrderCreateReq req) {
        ResourceDAO resourceDAO = resourceService.findById(req.getResourceId()).orElseThrow(() -> new JpaException(1, "资源不存在"));

        OrderDAO orderDAO = new OrderDAO();
        orderDAO.setId(snowflakeIdWorker.nextId());
        orderDAO.setUserId(req.getUserId());
        orderDAO.setResourceId(req.getResourceId());
        orderDAO.setResourceName(resourceDAO.getResourceName());
        orderDAO.setResourceLogo(resourceDAO.getResourceLogo());
        orderDAO.setQuantity(req.getQuantity());
        orderService.save(orderDAO);
        return orderDAO;
    }

    @Override
    public CompletableFuture<OrderCreateRsp> noticeCompletableFuture(OrderDAO orderDAO) {

        CompletableFuture<Void> checkUp1111Future = CompletableFuture.runAsync(() -> {
            log.warn("通知就是打个日志11111111 orderDAO:{}", JSON.toJSONString(orderDAO));
        }, threadPoolTaskExecutor).exceptionally(ex -> {
            log.error("就是出了异常11111111 id:{}", orderDAO.getId(), ex);
            return null;
        });

        CompletableFuture<Void> checkUp2222Future = CompletableFuture.runAsync(() -> {
            log.warn("通知就是打个日志2222222222 orderDAO:{}", JSON.toJSONString(orderDAO));
        }, threadPoolTaskExecutor).exceptionally(ex -> {
            log.error("就是出了异常2222222 id:{}", orderDAO.getId(), ex);
            return null;
        });
        //等待校验完成
        CompletableFuture.allOf(checkUp1111Future, checkUp2222Future).join();

        OrderCreateRsp orderCreateRsp = new OrderCreateRsp();
        CompletableFuture<OrderCreateRsp> checkUp3333Future = CompletableFuture.supplyAsync(() -> {
            log.warn("通知就是打个日志3333333333 orderDAO:{}", JSON.toJSONString(orderDAO));
            orderCreateRsp.setId(orderDAO.getId());
            orderCreateRsp.setResourceId(orderDAO.getResourceId());
            orderCreateRsp.setResourceLogo(orderDAO.getResourceLogo());
            orderCreateRsp.setResourceName(orderDAO.getResourceName());
            orderCreateRsp.setUserId(orderDAO.getUserId());
            orderCreateRsp.setQuantity(orderDAO.getQuantity());
            return orderCreateRsp;
        }, threadPoolTaskExecutor).exceptionally(ex -> {
            log.error("就是出了异常3333333333 id:{}", orderDAO.getId(), ex);
            return orderCreateRsp;
        });
        return checkUp3333Future;
    }

    @Override
    @Async("asyncExecutor")
    public void asyncProvide(OrderDAO orderDAO) {
        log.warn("发货就是打个日志333333333 orderDAO:{}", JSON.toJSONString(orderDAO));
    }
}
