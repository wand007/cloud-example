package com.cloud.example.jpa.feign;

import com.cloud.example.base.ResultResponse;
import com.cloud.example.jpa.hystrix.HystrixOrderFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-08-05
 */
@FeignClient(name = "${server-feign-name.order}", path = "/order", fallbackFactory = HystrixOrderFeign.class)
public interface FeignOrder {


    @RequestMapping(value = "/findDetail", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResultResponse findDetail(@RequestParam("id") String id);
}
