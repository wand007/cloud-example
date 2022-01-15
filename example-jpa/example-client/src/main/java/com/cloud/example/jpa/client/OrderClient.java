package com.cloud.example.jpa.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.jpa.feign.FeignOrder;
import com.cloud.example.jpa.service.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-08-05
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderClient extends BaseClient implements FeignOrder {

    @Resource
    OrderServiceImpl orderService;


    @RequestMapping(value = "/findDetail", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    @Override
    public ResultResponse findDetail(@RequestParam("id") String id) {

        return ResultResponse.success();
    }
}
