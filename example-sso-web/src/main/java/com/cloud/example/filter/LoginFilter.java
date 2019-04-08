package com.cloud.example.filter;

import com.cloud.example.base.BodyReaderHttpServletRequestWrapper;
import com.cloud.example.domain.sso.SsoUser;
import com.cloud.example.login.SsoWebLoginHelper;
import com.cloud.example.utils.IpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SsoWebLoginHelper ssoWebLoginHelper;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("登录过滤器初始化");
    }


    /**
     * 不需要强制登陆
     */
    private static final Set<String> notLoginList = new HashSet<String>() {
        {
            add("/login");
            add("/logout");
        }
    };


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //请求路径
        String servletPath = request.getServletPath();
        String origin = request.getHeader("Origin");
        //设置跨域
        if (StringUtils.isNotBlank(origin)) {
            if (request.getHeader("Origin").contains("gxcards.com") || origin.contains("localhost")) {
                response.setCharacterEncoding("utf-8");
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Origin", origin);
            }
        }

        if (request.getMethod().equals("OPTIONS")) {
            return;
        }

        BodyReaderHttpServletRequestWrapper requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        }

        //获取ip
        String ip = IpUtils.getRequestIp(request);
        request.setAttribute("ip", ip);

        //不需要登陆
        if (notLoginList.contains(servletPath)) {
            filterChain.doFilter(requestWrapper, response);
            return;
        }


        //设置用户信息
        SsoUser ssoUser = ssoWebLoginHelper.loginCheck(requestWrapper, response);
        if (null == ssoUser) {
            return;
        }

        //设置用户信息
        request.setAttribute("ssoUser", ssoUser);

        filterChain.doFilter(requestWrapper, response);
        return;
    }

    @Override
    public void destroy() {
        logger.info("登录过滤器已销毁");
    }


}
