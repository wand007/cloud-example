package com.cloud.example.service.impl;

import com.cloud.example.dao.UserRepository;
import com.cloud.example.domain.UserDAO;
import com.cloud.example.service.ISsoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Slf4j
@Service
public class SsoService implements ISsoService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDAO checkPassword(String username, String password) {
        UserDAO userDAO = userRepository.findByNickNameAndPassword(username, password);
        return userDAO;
    }
}
