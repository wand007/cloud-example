package com.cloud.example.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @author 咚咚锵
 * @date 2022/1/15 下午1:34
 * @description 自定义异常码
 */
@Getter
@ToString
public enum ResCodeEnum {

    LOCK_FAILED(1060000, "分布式加锁失败：%s"),
    ;

    private final Integer code;

    private final String message;

    ResCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
