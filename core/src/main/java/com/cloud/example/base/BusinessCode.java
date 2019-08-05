package com.cloud.example.base;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
public enum BusinessCode {

    /**
     * 成功状态码标志, 请求成功后必须返回该状态码
     * 可在代码中直接使用 SUCCESS, 如果有特殊需要, 自己定义状态码
     * 自己定义方式: COMMON_SUCCESS_XXX(I, "某某某")
     */
    SUCCESS(10000, "操作成功"),
    /**
     * 基础失败状态码标志
     */
    ERROR(50000, "操作已受理,请稍后再试"),
    /**
     * 此状态码前端直接提示
     */
    ALERT_MESSAGE(12000, "前端直接提示的 指导用户进一步操作的信息"),

    PARAMS_ERROR(40003, "参数异常"),
    ;

    private int code;
    private String desc;


    BusinessCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static BusinessCode parse(int code) {
        BusinessCode[] values = BusinessCode.values();

        for (BusinessCode value : values) {
            if (code == value.getCode()) {
                return value;
            }
        }

        throw new BusinessException("没找到此业务代码 : " + code);
    }
}
