package com.cloud.example.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.domain.UserDAO;
import com.cloud.example.domain.UserParam;
import com.cloud.example.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/8
 */

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserClient extends BaseClient {

    @Autowired
    IUserService iUserService;


    @ResponseBody
    @RequestMapping(value = "/findDetail")
    public ResultResponse<UserDAO> findDetail(String id) {
        UserDAO userDAO = iUserService.findOne(id);
        return ResultResponse.success(userDAO);
    }


}
