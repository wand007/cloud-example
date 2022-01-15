package com.cloud.example.jpa.client;

import com.cloud.example.base.ResultResponse;
import com.cloud.example.jpa.aggservice.OrderAggService;
import com.cloud.example.jpa.comm.RLockUtil;
import com.cloud.example.jpa.feign.FeignOrder;
import com.cloud.example.jpa.jpa.domain.OrderDAO;
import com.cloud.example.jpa.param.req.OrderCreateReq;
import com.cloud.example.jpa.param.rsp.OrderCreateRsp;
import com.cloud.example.jpa.service.OrderService;
import com.cloud.example.jpa.strategy.UserCheckContext;
import com.cloud.example.jpa.strategy.UserCheckStrategy;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-08-05
 */
@Slf4j
@Setter
@RestController
@RequestMapping("/order")
public class OrderClient implements FeignOrder {

    @Resource
    RLockUtil rLockUtil;

    @Resource
    UserCheckContext userCheckContext;

    @Resource
    OrderService orderService;

    @Resource
    OrderAggService orderAggService;


    @RequestMapping(value = "/findDetail", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Override
    public ResultResponse findDetail(@RequestParam("id") String id) {
        return ResultResponse.success();
    }

    @Override
    public ResultResponse<OrderCreateRsp> save(OrderCreateReq req) {
        log.warn("start --------save");
        UserCheckStrategy userCheckStrategy = userCheckContext.getContext(req.getSource());
        //检查参数
        userCheckStrategy.check(req);
        return rLockUtil.useRLock("ORDER_REDIS_LOCK_" + req.getUserId(),
                20, 30, () -> {
                    log.warn("useRLock --------save");
                    OrderDAO orderDAO = orderService.findByUserId(req.getUserId()).orElseGet(() -> {
                        log.warn("save --------save");
                        return orderAggService.save(req);
                    });
                    //异步发货
                    orderAggService.asyncProvide(orderDAO);
                    //异步通知
                    CompletableFuture<OrderCreateRsp> completableFuture = orderAggService.noticeCompletableFuture(orderDAO);
                    OrderCreateRsp orderCreateRsp = completableFuture.get(30, TimeUnit.SECONDS);

                    log.warn("end --------save");
                    return ResultResponse.success(orderCreateRsp);
                });
    }
}
