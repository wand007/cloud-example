package com.cloud.example.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Data
public class BusinessException extends RuntimeException implements Serializable {

    private int code;

    private String msg;


    public BusinessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(String msg) {
        this.code = BusinessCode.ALERT_MESSAGE.getCode();
        this.msg = msg;
    }

    public BusinessException(BusinessCode error) {
        this.code = error.getCode();
        this.msg = error.getDesc();
    }

}
