package com.cloud.example.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Slf4j
@Component
public class BaseClient {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResultResponse exceptionHandler(Exception e) {
        log.error("SystemException", e);
        return ResultResponse.error();
    }

    /**
     * hibernate 参数校验出错会抛出 ConstraintViolationException 异常
     * 在此方法中处理，将错误信息输出
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Object exceptionHandler(ValidationException exception) {
        String errorInfo = "";
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;

            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();

            for (ConstraintViolation<?> item : violations) {
                errorInfo = errorInfo + item.getMessage();
            }
        }
        return new ResultResponse(BusinessCode.ALERT_MESSAGE.getCode(), errorInfo);
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
