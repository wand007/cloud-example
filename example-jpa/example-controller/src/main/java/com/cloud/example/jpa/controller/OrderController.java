package com.cloud.example.jpa.controller;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.jpa.feign.FeignOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-08-05
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController extends BaseClient {

    @Resource
    FeignOrder feignOrder;

    @RequestMapping(value = "/findDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResultResponse findDetail(String id) {

        return feignOrder.findDetail(id);
    }
}
