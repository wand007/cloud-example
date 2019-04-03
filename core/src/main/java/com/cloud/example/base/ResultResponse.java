package com.cloud.example.base;

import java.io.Serializable;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
public class ResultResponse implements Serializable {
    private int statusCode;
    private String statusText;
    private Object data;
    private String currentTimeMillis;


    public ResultResponse() {
        this.statusCode = BusinessCode.SUCCESS.getCode();
        this.data = "";
        this.statusText = "success";
        this.currentTimeMillis = System.currentTimeMillis() + "";
    }

    public ResultResponse(int statusCode,
                          String statusText,
                          Object data,
                          String currentTimeMillis) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.data = data;
        this.currentTimeMillis = currentTimeMillis;
    }

    public ResultResponse(int statusCode,
                          String statusText) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.data = "";
        this.currentTimeMillis = System.currentTimeMillis() + "";
    }

    public static ResultResponse fromBusinessCode(BusinessCode businessCode) {
        return new ResultResponse(businessCode.getCode(),
                businessCode.getDesc());
    }

    public static ResultResponse success(Object data) {
        return new ResultResponse(BusinessCode.SUCCESS.getCode(), "success", data, System.currentTimeMillis() + "");
    }

    public static ResultResponse success() {
        return new ResultResponse(BusinessCode.SUCCESS.getCode(), "success", "", System.currentTimeMillis() + "");
    }

    public static ResultResponse error() {
        return new ResultResponse(BusinessCode.ERROR.getCode(), BusinessCode.ERROR.getDesc(), "", System.currentTimeMillis()
                + "");
    }


    public int getStatusCode() {
        return statusCode;
    }

    public ResultResponse setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatusText() {
        return statusText;
    }

    public ResultResponse setStatusText(String statusText) {
        this.statusText = statusText;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResultResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public String getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    public void setCurrentTimeMillis(String currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }


}
