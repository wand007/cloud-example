package com.cloud.example.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */
@Slf4j
@Component
public class BaseController {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResultResponse exceptionHandler(Exception e) {
        log.error("SystemException", e);
        return ResultResponse.error();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    ResultResponse exceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return new ResultResponse(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    ResultResponse exceptionHandler(BindException e) {
        return new ResultResponse(400, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
