package com.jpa.example.jpa.dao;

import com.jpa.example.jpa.domain.ResourceDetailDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Repository
public interface ResourceDetailRepository extends JpaRepository<ResourceDetailDAO, String>, JpaSpecificationExecutor<ResourceDetailDAO> {


}
