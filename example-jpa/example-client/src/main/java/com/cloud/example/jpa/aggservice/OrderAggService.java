package com.cloud.example.jpa.aggservice;

import com.cloud.example.jpa.jpa.domain.OrderDAO;
import com.cloud.example.jpa.param.req.OrderCreateReq;
import com.cloud.example.jpa.param.rsp.OrderCreateRsp;

import java.util.concurrent.CompletableFuture;

/**
 * @author 咚咚锵
 * @date 2022/1/15 下午1:38
 * @description TODO
 */
public interface OrderAggService {
    OrderDAO save(OrderCreateReq req);

    CompletableFuture<OrderCreateRsp> noticeCompletableFuture(OrderDAO orderDAO);

    void asyncProvide(OrderDAO orderDAO);

}
