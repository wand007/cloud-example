package com.cloud.example.service.impl;

import com.cloud.example.dao.UserRepository;
import com.cloud.example.domain.UserDAO;
import com.cloud.example.service.IUserService;
import com.cloud.example.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/8
 */
@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDAO findOne(String id) {
        Optional<UserDAO> optional = userRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

}
