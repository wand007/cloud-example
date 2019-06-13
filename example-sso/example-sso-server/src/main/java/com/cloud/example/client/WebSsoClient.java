package com.cloud.example.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.domain.UserDAO;
import com.cloud.example.service.ISsoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Slf4j
@RestController
@RequestMapping(value = "/sso")
public class WebSsoClient extends BaseClient {


    @Autowired
    ISsoService iSsoService;

    @ResponseBody
    @RequestMapping(value = "/checkPassword")
    public ResultResponse<UserDAO> checkPassword(String username, String password) {
        UserDAO userDAO = iSsoService.checkPassword(username, password);
        return ResultResponse.success(userDAO);
    }





}
