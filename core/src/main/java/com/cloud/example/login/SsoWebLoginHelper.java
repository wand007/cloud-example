package com.cloud.example.login;

import com.cloud.example.config.SsoConfig;
import com.cloud.example.domain.sso.SsoUser;
import com.cloud.example.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuxueli 2018-04-03
 */
@Component
public class SsoWebLoginHelper {

    @Autowired
    SsoLoginStore ssoLoginStore;
    @Autowired
    SsoTokenLoginHelper ssoTokenLoginHelper;

    /**
     * make client sessionId
     *
     * @param ssoUser
     * @return
     */
    public String makeSessionId(SsoUser ssoUser) {
        String sessionId = ssoUser.getUserId().concat("_").concat(ssoUser.getVersion());
        return sessionId;
    }

    /**
     * client login
     *
     * @param response
     * @param sessionId
     * @param ifRemember true: cookie not expire, false: expire when browser close （server cookie）
     * @param ssoUser
     */
    public void login(HttpServletResponse response,
                      String sessionId,
                      SsoUser ssoUser,
                      boolean ifRemember) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            throw new RuntimeException("parseStoreKey Fail, sessionId:" + sessionId);
        }

        ssoLoginStore.put(storeKey, ssoUser);
        CookieUtil.set(response, SsoConfig.SSO_SESSIONID, sessionId, ifRemember);
    }

    /**
     * client logout
     *
     * @param request
     * @param response
     */
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) {

        String cookieSessionId = CookieUtil.getValue(request, SsoConfig.SSO_SESSIONID);
        if (cookieSessionId == null) {
            return;
        }

        String storeKey = SsoSessionIdHelper.parseStoreKey(cookieSessionId);
        if (storeKey != null) {
            ssoLoginStore.remove(storeKey);
        }

        CookieUtil.remove(request, response, SsoConfig.SSO_SESSIONID);
    }


    /**
     * login check
     *
     * @param request
     * @param response
     * @return
     */
    public SsoUser loginCheck(HttpServletRequest request, HttpServletResponse response) {

        String cookieSessionId = CookieUtil.getValue(request, SsoConfig.SSO_SESSIONID);

        // cookie user
        SsoUser ssoUser = ssoTokenLoginHelper.loginCheck(cookieSessionId);
        if (ssoUser != null) {
            return ssoUser;
        }

        // redirect user

        // remove old cookie
        this.removeSessionIdByCookie(request, response);

        // set new cookie
        String paramSessionId = request.getParameter(SsoConfig.SSO_SESSIONID);
        ssoUser = ssoTokenLoginHelper.loginCheck(paramSessionId);
        if (ssoUser != null) {
            CookieUtil.set(response, SsoConfig.SSO_SESSIONID, paramSessionId, false);    // expire when browser close （client cookie）
            return ssoUser;
        }

        return null;
    }


    /**
     * client logout, cookie only
     *
     * @param request
     * @param response
     */
    public void removeSessionIdByCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.remove(request, response, SsoConfig.SSO_SESSIONID);
    }

    /**
     * get sessionid by cookie
     *
     * @param request
     * @return
     */
    public String getSessionIdByCookie(HttpServletRequest request) {
        String cookieSessionId = CookieUtil.getValue(request, SsoConfig.SSO_SESSIONID);
        return cookieSessionId;
    }


}
