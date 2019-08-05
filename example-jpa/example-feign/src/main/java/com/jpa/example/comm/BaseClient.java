package com.jpa.example.comm;

import com.cloud.example.base.BusinessCode;
import com.cloud.example.base.BusinessException;
import com.cloud.example.base.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @Date 2019/2/13
 */
@Component
public class BaseClient {

    private static final Logger logger = LoggerFactory.getLogger(BaseClient.class);


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultResponse exceptionHandler(Exception e) {
        logger.error("SystemException,e:" + e.getMessage(), e);
        return ResultResponse.fromBusinessCode(BusinessCode.ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResultResponse exceptionHandler(BusinessException e) {
        logger.error("BusinessException,e:" + e.getMessage(), e);
        if (BusinessCode.ERROR.getCode() == e.getCode()) {
            return new ResultResponse(BusinessCode.ALERT_MESSAGE.getCode(), e.getMsg());
        }
        return new ResultResponse(e.getCode(), e.getMsg());
    }

    /**
     * hibernate 参数校验出错会抛出 ConstraintViolationException 异常
     * 在此方法中处理，将错误信息输出
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public ResultResponse exceptionHandler(ValidationException e) {
        logger.error("ValidationException,e:" + e.getMessage(), e);
        StringBuilder errorInfo = new StringBuilder("");
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();

            for (ConstraintViolation<?> item : violations) {
                errorInfo.append(item.getMessage());
            }
        }
        return new ResultResponse(BusinessCode.ALERT_MESSAGE.getCode(), errorInfo.toString());
    }


    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultResponse exceptionHandler(BindException e) {
        logger.error("BindException,e:" + e.getMessage(), e);
        return new ResultResponse(BusinessCode.ALERT_MESSAGE.getCode(), e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }


}
