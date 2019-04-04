package com.cloud.example.enums;

import com.cloud.example.base.BusinessException;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */
public enum ERequestMethod {
    GET("GET", "查询操作"),
    HEAD("HEAD", "HEAD请求只会返回首部的信息，不会返回相应体"),
    POST("POST", "POST方法把数据都存放在body里面"),
    PUT("PUT", "用于改变某些内容"),
    PATCH("PATCH", "这个方法不太常见，是servlet 3.0提供的方法，主要用于更新部分字段"),
    DELETE("DELETE", "删除某些资源"),
    OPTIONS("OPTIONS", "客户端查看服务器端的性能"),
    TRACE("TRACE", "回显服务器的请求");

    private String method;
    private String des;

    ERequestMethod(String method, String des) {
        this.method = method;
        this.des = des;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public static ERequestMethod parse(String val) {
        ERequestMethod[] values = ERequestMethod.values();
        for (ERequestMethod temp : values) {
            if (val.equals(temp.getMethod())) {
                return temp;
            }
        }
        throw new BusinessException("没有找到此状态 : " + val);
    }

}
