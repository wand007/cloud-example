package com.cloud.example.jpa.service.impl;

import com.cloud.example.jpa.jpa.dao.UserRepository;
import com.cloud.example.jpa.jpa.domain.UserDAO;
import com.cloud.example.jpa.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author 咚咚锵
 * @date 2022/1/15 下午2:25
 * @description TODO
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserRepository userRepository;


    @Override
    public Optional<UserDAO> findById(String userId) {
        return userRepository.findById(userId);
    }
}
