package com.cloud.example.controller;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.client.IUserFeign;
import com.cloud.example.domain.UserParam;
import com.cloud.example.domain.sso.SsoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController extends BaseClient {


    @Autowired
    IUserFeign iUserFeign;

    @ResponseBody
    @RequestMapping(value = "/findDetail")
    public ResultResponse findDetail(@RequestAttribute(name = "ssoUser") SsoUser ssoUser) {
        return iUserFeign.findDetail(ssoUser.getUserId());
    }


    @ResponseBody
    @RequestMapping(value = "/add")
    public ResultResponse add(@RequestBody UserParam param) {
        return iUserFeign.add(param);
    }
}
