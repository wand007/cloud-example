package com.nacos.example.controller;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.nacos.example.client.IUserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private RestTemplate restTemplate;
    @Value("${service.url.user}")
    private String creditServiceUrl;

    @Autowired
    IUserFeign iUserFeign;


    @ResponseBody
    @RequestMapping(value = "/findDetail")
    public ResultResponse findDetail(String userId) {
        return iUserFeign.findDetail(userId);
    }
    @ResponseBody
    @RequestMapping(value = "/findException")
    public ResultResponse findException(String userId) {
        return iUserFeign.findException(userId);
    }


    @ResponseBody
    @RequestMapping(value = "/getNacosValue")
    public ResultResponse getNacosValue() {
        return restTemplate.postForObject(creditServiceUrl + "/user/getNacosValue", null, ResultResponse.class);
    }


    @Value(value = "${config-client:haha}")
    private String configClient;

    @ResponseBody
    @RequestMapping(value = "/getConfig")
    public ResultResponse getConfig() {
        return ResultResponse.success(configClient);
    }


}
