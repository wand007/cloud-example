package com.jpa.example.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.jpa.example.feign.FeignOrder;
import com.jpa.example.service.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-08-05
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderClient extends BaseClient implements FeignOrder {

    @Autowired
    OrderServiceImpl orderService;


    @RequestMapping(value = "/findDetail", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    @Override
    public ResultResponse findDetail(@RequestParam("id") String id) {

        return ResultResponse.success();
    }
}
