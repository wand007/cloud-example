package com.cloud.example.client;

import com.cloud.example.base.ResultResponse;
import com.cloud.example.client.fallback.UserFallback;
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
        name = "${service.url.user}", fallback = UserFallback.class
)
public interface IUserFeign {


    @ResponseBody
    @RequestMapping(value = "/findDetail", method = RequestMethod.POST)
    public ResultResponse findDetail(@RequestParam(value = "id") String id);
}
