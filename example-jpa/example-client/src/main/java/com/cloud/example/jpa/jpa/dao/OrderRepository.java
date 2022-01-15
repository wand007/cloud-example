package com.cloud.example.jpa.jpa.dao;

import com.cloud.example.jpa.jpa.domain.OrderDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderDAO, String>, JpaSpecificationExecutor<OrderDAO> {
    Optional<OrderDAO> findByUserId(String userId);

}
