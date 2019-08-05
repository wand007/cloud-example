package com.cloud.example.dao;

import com.cloud.example.domain.ResourceDetailDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
public interface ResourceDetailRepository extends JpaRepository<ResourceDetailDAO, String>, JpaSpecificationExecutor<ResourceDetailDAO> {


}
