package com.cloud.example.jpa.exception;

/**
 * @author 咚咚锵
 * @date 2022/1/15 下午1:32
 * @description 自定义异常
 */
public class JpaException extends RuntimeException {

    private int code;
    private Object extra;

    public JpaException(String message) {
        super(message);
    }

    public JpaException(int code) {
        this("code:" + code);
        this.code = code;
    }

    public JpaException(int code, String message) {
        this(message);
        this.code = code;
    }

    public JpaException(int code, String message, Object extra) {
        this(code, message);
        this.extra = extra;
    }

    public int getCode() {
        return this.code;
    }

    public Object getExtra() {
        return this.extra;
    }
}
