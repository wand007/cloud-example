package com.cloud.example.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

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
        this.statusText = "success";
        this.data = (T) new HashMap(1);
        this.currentTimeMillis = String.valueOf(System.currentTimeMillis());
    }

    public ResultResponse(int statusCode, String statusText, T t, String currentTimeMillis) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.data = t;
        this.currentTimeMillis = currentTimeMillis;
    }

    public ResultResponse(int statusCode, String statusText) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.data = (T) new HashMap(1);
        this.currentTimeMillis = String.valueOf(System.currentTimeMillis());
    }

    public ResultResponse(int statusCode, String statusText, T t) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.data = t;
        this.currentTimeMillis = String.valueOf(System.currentTimeMillis());
    }

    public static ResultResponse fromBusinessCode(BusinessCode businessCode) {
        return fromBusinessCode(businessCode, new HashMap(1));
    }

    public static <T> ResultResponse<T> fromBusinessCode(BusinessCode businessCode, T t) {
        return new ResultResponse(businessCode.getCode(), businessCode.getDesc(), t, String.valueOf(System.currentTimeMillis()));
    }

    public static ResultResponse success(Object data) {
        return new ResultResponse(BusinessCode.SUCCESS.getCode(), "success", data, String.valueOf(System.currentTimeMillis()));
    }

    public static ResultResponse success() {
        return new ResultResponse(BusinessCode.SUCCESS.getCode(), "success", new HashMap(1), String.valueOf(System.currentTimeMillis()));
    }

    @JsonIgnore
    public boolean isSuccess() {
        return BusinessCode.SUCCESS.getCode() == this.statusCode;
    }


}
