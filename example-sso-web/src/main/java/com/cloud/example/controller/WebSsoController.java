package com.cloud.example.controller;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.BusinessCode;
import com.cloud.example.base.BusinessException;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.client.ISsoFeign;
import com.cloud.example.domain.UserParam;
import com.cloud.example.domain.sso.SsoUser;
import com.cloud.example.domain.sso.SsoUserBase;
import com.cloud.example.domain.sso.SsoUserWx;
import com.cloud.example.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Slf4j
@RestController
@RequestMapping(value = "/sso")
public class WebSsoController extends BaseClient {

    @Autowired
    ISsoFeign iSsoFeign;

    @ResponseBody
    @RequestMapping(value = "/login")
    public ResultResponse login(String username, String password, Boolean ifRemember) {
        ResultResponse resultResponse = iSsoFeign.checkPassword(username, password);
        if (resultResponse == null || BusinessCode.SUCCESS.getCode() == resultResponse.getStatusCode()) {
            throw new BusinessException("用户名或密码错误");
        }
        UserParam param = JsonUtils.toObject(JsonUtils.toJson(resultResponse.getData()),UserParam.class);

        SsoUser ssoUser = new SsoUser();
        ssoUser.setNickName(param.getNickName());
        ssoUser.setAvatarUrl(param.getAvatarUrl());
        ssoUser.setPhoneNo(param.getPhoneNo());
        ssoUser.setUserId(param.getId());
        SsoUserWx ssoUserWx = new SsoUserWx();
        ssoUserWx.setNickName(ssoUser.getNickName());
        ssoUserWx.setAvatarUrl(ssoUser.getAvatarUrl());
        ssoUserWx.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
        ssoUserWx.setLandingTime(System.currentTimeMillis());
        Map<String, SsoUserBase> map = new HashMap<>();
        map.put("WX",ssoUserWx);
        ssoUser.setUserBaseMap(map);

        return ResultResponse.success();
    }


}
