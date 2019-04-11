package com.cloud.example.controller;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.BusinessException;
import com.cloud.example.base.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("user")
public class UserController extends BaseClient {


    @Value(value = "${server.port:1}")
    private String serverName;

    @ResponseBody
    @RequestMapping(value = "/getNacosValue")
    public ResultResponse getNacosValue() {

        return ResultResponse.success(serverName + "哈哈");
    }

    @ResponseBody
    @RequestMapping(value = "/findDetail")
    public ResultResponse findDetail() throws InterruptedException {
        log.info("尼古拉斯蛋蛋");
        return ResultResponse.success(serverName + "尼古拉斯蛋蛋");
    }

    @ResponseBody
    @RequestMapping(value = "/findException")
    public ResultResponse findException() {

        throw new BusinessException("熔断异常测试");
    }


}
