package com.cloud.example.domain.sso;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Data
public class SsoUserBase implements Serializable {
    private static final long serialVersionUID = -6855187044592567887L;
    /**
     * 登陆凭证
     */
    private String token;
    /**
     * 浏览器用户代理
     */
    private String userAgent;
    /**
     * 登陆时间
     */
    private Long landingTime;
}
