package com.cloud.example.dao;

import com.cloud.example.domain.OrderDAO;
import com.cloud.example.domain.ResourceDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
public interface OrderRepository extends JpaRepository<OrderDAO, String>, JpaSpecificationExecutor<OrderDAO> {
}
