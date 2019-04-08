package com.cloud.example.domain.sso;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Data
public class SsoUser implements Serializable {
    private static final long serialVersionUID = 7767339123830568453L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 手机号
     */
    private String phoneNo;

    private String version;

    private int expireMinite;

    private long expireFreshTime;

    /**
     * 用户缓存详情
     */
    private Map<String, SsoUserBase> userBaseMap;
}
