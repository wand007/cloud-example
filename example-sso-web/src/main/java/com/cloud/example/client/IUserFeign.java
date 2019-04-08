package com.cloud.example.client;

import com.cloud.example.base.ResultResponse;
import com.cloud.example.client.fallback.UserFallback;
import com.cloud.example.domain.UserParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/user/findDetail", method = RequestMethod.POST)
    ResultResponse findDetail(@RequestParam(value = "id") String id);

    @ResponseBody
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    ResultResponse add(@RequestBody UserParam param);
}
