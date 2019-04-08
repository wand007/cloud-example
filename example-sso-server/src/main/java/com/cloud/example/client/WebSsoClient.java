package com.cloud.example.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.domain.UserParam;
import lombok.extern.slf4j.Slf4j;
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

    @ResponseBody
    @RequestMapping(value = "/login")
    public ResultResponse login(String param) {

        return ResultResponse.success();
    }





}
