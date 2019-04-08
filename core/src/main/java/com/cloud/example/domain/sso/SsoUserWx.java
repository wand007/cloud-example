package com.cloud.example.domain.sso;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/8
 */
@Data
public class SsoUserWx extends SsoUserBase implements Serializable{

    private static final long serialVersionUID = -5876323408816468274L;
    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatarUrl;
}
