package com.cloud.example.jpa.service.impl;

import com.cloud.example.utils.SnowflakeIdWorker;
import com.cloud.example.jpa.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-08-05
 */
@Service
public class OrderService implements OrderServiceImpl {
    @Resource
    SnowflakeIdWorker snowflakeIdWorker;
}
