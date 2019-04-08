package com.cloud.example.client;

import com.cloud.example.base.ResultResponse;
import com.cloud.example.client.fallback.SsoFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */

@FeignClient(
        //服务名
        name = "${service.url.user}", fallback = SsoFallback.class
)
public interface ISsoFeign {


    @ResponseBody
    @RequestMapping(value = "/sso/checkPassword", method = RequestMethod.POST)
    ResultResponse checkPassword(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password);

}
