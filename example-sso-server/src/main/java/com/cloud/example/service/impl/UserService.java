package com.cloud.example.service.impl;

import com.cloud.example.dao.UserRepository;
import com.cloud.example.domain.UserDAO;
import com.cloud.example.domain.UserParam;
import com.cloud.example.service.IUserService;
import com.cloud.example.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return userRepository.findById(id).get();
    }

    @Override
    public UserDAO add(UserParam param) {
        UserDAO userDAO = new UserDAO();
        userDAO.setId(snowflakeIdWorker.nextId());
        userDAO.setAvatarUrl(param.getAvatarUrl());
        userDAO.setPhoneNo(param.getPhoneNo());
        userDAO.setNickName(param.getNickName());
        userDAO.setPassword(param.getPassword());
        return userRepository.save(userDAO);
    }
}
