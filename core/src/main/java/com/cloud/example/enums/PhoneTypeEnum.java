package com.cloud.example.enums;


/**
 * @author peng
 * @description
 * @date 2018/7/16
 * @update by peng
 */
public enum PhoneTypeEnum {

    ERROR(-1, "ERROR", "非法"),
    UNKNOWN(0, "UNKNOWN", "未知"),
    CHINA_UNICOM(1, "CHINA_UNICOM", "中国联通"),
    CHINA_MOBILE(2, "CHINA_MOBILE", "中国移动"),
    CHINA_TELECOM(3, "CHINA_TELECOM", "中国电信");


    private int type;
    private String name;
    private String description;

    PhoneTypeEnum(int type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
