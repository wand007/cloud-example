package com.example.demo.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@RestController
@RequestMapping(value = "/sso")
public class WebSsoClient extends BaseClient {


    @RequestMapping(value = "/login")
    public ResultResponse login(String param) {

        return ResultResponse.success();
    }

}
