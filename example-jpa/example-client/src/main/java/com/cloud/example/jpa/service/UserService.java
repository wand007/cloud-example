package com.cloud.example.jpa.service;

import com.cloud.example.jpa.jpa.domain.UserDAO;

import java.util.Optional;

/**
 * @author 咚咚锵
 * @date 2022/1/15 下午2:25
 * @description TODO
 */
public interface UserService {
    Optional<UserDAO> findById(String userId);
}
