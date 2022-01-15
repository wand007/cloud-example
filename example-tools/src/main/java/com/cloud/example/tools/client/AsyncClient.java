package com.cloud.example.tools.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.service.IAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/8
 */

@Slf4j
@RestController
@RequestMapping(value = "/async")
public class AsyncClient extends BaseClient {

    @Resource
    IAsyncService iAsyncService;


    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultResponse asyncGet() {
        log.info("asyncGet---------1");
        iAsyncService.async();
        log.info("asyncGet---------4");
        return ResultResponse.success();
    }

    @ResponseBody
    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public ResultResponse asyncException() {
        log.info("asyncGet---------1");
        iAsyncService.asyncException();
        log.info("asyncGet---------4");
        return ResultResponse.success();
    }

    @ResponseBody
    @RequestMapping(value = "/catchException", method = RequestMethod.GET)
    public ResultResponse asyncCatchException() {
        log.info("asyncGet---------1");
        iAsyncService.asyncCatchException();
        log.info("asyncGet---------4");
        return ResultResponse.success();
    }
}
