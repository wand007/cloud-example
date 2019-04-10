package com.cloud.example.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.client.IUserFeign;
import com.cloud.example.domain.sso.SsoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestAttribute;
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
    @RequestMapping(value = "/getDetail")
    public ResultResponse getDetail(String userId) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>(2);
        multiValueMap.set("userId", userId);
        return restTemplate.postForObject(creditServiceUrl + "/user/findDetail", multiValueMap, ResultResponse.class);
    }


    @NacosValue(value = "${server.port:1}", autoRefreshed = true)

    private String serverName;

    @Value(value = "${spring.profile.active:2}")
    private String active;

    @ResponseBody
    @RequestMapping(value = "/test")
    public ResultResponse test() {
        return ResultResponse.success(serverName + active);
    }


}
