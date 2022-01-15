package com.cloud.example.jpa.service;

import com.cloud.example.jpa.jpa.domain.OrderDAO;

import java.util.Optional;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-08-05
 */
public interface OrderService {
    /**
     * @param userId
     * @return
     */
    Optional<OrderDAO> findByUserId(String userId);

    /**
     * @param orderDAO
     */
    void save(OrderDAO orderDAO);
}
