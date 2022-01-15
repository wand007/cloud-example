package com.cloud.example.login;

import com.cloud.example.config.SsoConfig;
import com.cloud.example.domain.sso.SsoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/8
 */
@Component
public class SsoTokenLoginHelper {

    @Resource
    SsoLoginStore ssoLoginStore;

    /**
     * client login
     *
     * @param sessionId
     * @param xxlUser
     */
    public void login(String sessionId, SsoUser xxlUser) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            throw new RuntimeException("parseStoreKey Fail, sessionId:" + sessionId);
        }

        ssoLoginStore.put(storeKey, xxlUser);
    }

    /**
     * client logout
     *
     * @param sessionId
     */
    public void logout(String sessionId) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            return;
        }

        ssoLoginStore.remove(storeKey);
    }

    /**
     * client logout
     *
     * @param request
     */
    public void logout(HttpServletRequest request) {
        String headerSessionId = request.getHeader(SsoConfig.SSO_SESSIONID);
        logout(headerSessionId);
    }


    /**
     * login check
     *
     * @param sessionId
     * @return
     */
    public SsoUser loginCheck(String sessionId) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            return null;
        }

        SsoUser ssoUser = ssoLoginStore.get(storeKey);
        if (ssoUser != null) {
            String version = SsoSessionIdHelper.parseVersion(sessionId);
            if (ssoUser.getVersion().equals(version)) {

                // After the expiration time has passed half, Auto refresh
                if ((System.currentTimeMillis() - ssoUser.getExpireFreshTime()) > ssoUser.getExpireMinite() / 2) {
                    ssoUser.setExpireFreshTime(System.currentTimeMillis());
                    ssoLoginStore.put(storeKey, ssoUser);
                }

                return ssoUser;
            }
        }
        return null;
    }


    /**
     * login check
     *
     * @param request
     * @return
     */
    public SsoUser loginCheck(HttpServletRequest request) {
        String headerSessionId = request.getHeader(SsoConfig.SSO_SESSIONID);
        return loginCheck(headerSessionId);
    }
}
