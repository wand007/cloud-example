package com.jpa.example.jpa.dao;

import com.jpa.example.jpa.domain.OrderDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderDAO, String>, JpaSpecificationExecutor<OrderDAO> {
}
