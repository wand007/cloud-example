package com.cloud.example.service;

import com.cloud.example.domain.UserDAO;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
public interface ISsoService {

    public UserDAO checkPassword(String username, String password);
}
