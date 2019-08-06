package com.jpa.example.service.impl;

import com.cloud.example.utils.SnowflakeIdWorker;
import com.jpa.example.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-08-05
 */
@Service
public class OrderService implements OrderServiceImpl {
    @Autowired
    SnowflakeIdWorker snowflakeIdWorker;
}
