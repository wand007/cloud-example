package com.cloud.example.dao;

import com.cloud.example.domain.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Repository
public interface UserRepository extends JpaRepository<UserDAO, String>, JpaSpecificationExecutor<UserDAO> {
    /**
     * 根据登陆密码查用户记录
     *
     *
     * @param username
     * @param password
     * @return
     */
    UserDAO findByNickNameAndPassword(String username, String password);

}
