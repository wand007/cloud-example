package com.cloud.example.jpa.feign;

import com.cloud.example.base.ResultResponse;
import com.cloud.example.jpa.hystrix.HystrixOrderFeign;
import com.cloud.example.jpa.param.req.OrderCreateReq;
import com.cloud.example.jpa.param.rsp.OrderCreateRsp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-08-05
 */
@FeignClient(name = "${server-feign-name.order}", path = "/order", fallbackFactory = HystrixOrderFeign.class)
public interface FeignOrder {

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/findDetail", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResultResponse findDetail(@RequestParam("id") String id);

    /**
     * @param req
     * @return
     */
    @PostMapping(value = "/save")
    ResultResponse<OrderCreateRsp> save(@RequestBody OrderCreateReq req);
}
