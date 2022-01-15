package com.cloud.example.jpa.service.impl;

import com.cloud.example.jpa.jpa.dao.OrderRepository;
import com.cloud.example.jpa.jpa.domain.OrderDAO;
import com.cloud.example.jpa.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-08-05
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderRepository orderRepository;


    @Override
    public Optional<OrderDAO> findByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    /**
     * @param orderDAO
     */
    @Override
    public void save(OrderDAO orderDAO) {
        orderRepository.save(orderDAO);
    }
}
