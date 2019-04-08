package com.cloud.example.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Data
public class ResultResponse<T> implements Serializable {
    private int statusCode;
    private String statusText;
    private T data;
    private String currentTimeMillis;


    public ResultResponse() {
        this.statusCode = BusinessCode.SUCCESS.getCode();
        this.statusText = BusinessCode.SUCCESS.name();
        this.currentTimeMillis = System.currentTimeMillis() + "";
    }

    public ResultResponse(int statusCode, String statusText, T data, String currentTimeMillis) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.data = data;
        this.currentTimeMillis = currentTimeMillis;
    }

    public ResultResponse(int statusCode, String statusText) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.currentTimeMillis = System.currentTimeMillis() + "";
    }

    public static <T> ResultResponse<T> fromBusinessCode(BusinessCode businessCode) {
        return new ResultResponse(businessCode.getCode(), businessCode.getDesc());
    }

    public static <T> ResultResponse<T> success(Object data) {
        return new ResultResponse(BusinessCode.SUCCESS.getCode(), BusinessCode.SUCCESS.name(), data, System.currentTimeMillis() + "");
    }

    public static <T> ResultResponse<T> success() {
        return new ResultResponse(BusinessCode.SUCCESS.getCode(), BusinessCode.SUCCESS.name(), null, System
                .currentTimeMillis() + "");
    }

    public static <T> ResultResponse<T> error() {
        return new ResultResponse(BusinessCode.ERROR.getCode(), BusinessCode.ERROR.getDesc(), null, System
                .currentTimeMillis()
                + "");
    }


}
