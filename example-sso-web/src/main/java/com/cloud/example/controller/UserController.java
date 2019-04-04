package com.cloud.example.controller;

import com.cloud.example.base.BaseController;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.client.IUserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController extends BaseController {


    @Autowired
    IUserFeign iUserFeign;

    @ResponseBody
    @RequestMapping(value = "/findDetail")
    public ResultResponse findDetail(String id) {
        return iUserFeign.findDetail(id);
    }
}
