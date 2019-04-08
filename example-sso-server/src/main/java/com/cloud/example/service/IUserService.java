package com.cloud.example.service;

import com.cloud.example.domain.UserDAO;
import com.cloud.example.domain.UserParam;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/8
 */
public interface IUserService {

    UserDAO findOne(String id);

    UserDAO add(UserParam param);
}
