package com.cloud.example.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/8
 */
@Data
@ToString
public class UserParam implements Serializable {

    private static final long serialVersionUID = 3758188800822920284L;
    /**
     * 用户id
     */
    private String id;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 用户昵称
     */
    private String nickName;

}
