package com.cloud.example.login;

import com.cloud.example.config.SsoConfig;
import com.cloud.example.domain.sso.SsoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/8
 */
@Component
public class SsoLoginStore {

    @Autowired
    RedisTemplate redisTemplate;

    private static int redisExpireMinite = 1440;    // 1440 minite, 24 hour

    public void setRedisExpireMinite(int redisExpireMinite) {
        if (redisExpireMinite < 30) {
            redisExpireMinite = 30;
        }
        SsoLoginStore.redisExpireMinite = redisExpireMinite;
    }

    public int getRedisExpireMinite() {
        return redisExpireMinite;
    }

    /**
     * get
     *
     * @param storeKey
     * @return
     */
    public SsoUser get(String storeKey) {

        String redisKey = redisKey(storeKey);

        Object objectValue = redisTemplate.opsForValue().get(redisKey);
        if (objectValue != null) {
            SsoUser xxlUser = (SsoUser) objectValue;
            return xxlUser;
        }
        return null;
    }

    /**
     * remove
     *
     * @param storeKey
     */
    public void remove(String storeKey) {
        String redisKey = redisKey(storeKey);
        redisTemplate.delete(redisKey);
    }

    /**
     * put
     *
     * @param storeKey
     * @param ssoUser
     */
    public void put(String storeKey, SsoUser ssoUser) {
        String redisKey = redisKey(storeKey);
        Map<String, SsoUser> map = new HashMap<>(4);
        map.put(storeKey, ssoUser);

        redisTemplate.opsForValue().set(redisKey, ssoUser);  // minite to second
        redisTemplate.expire(redisKey, Long.valueOf(redisExpireMinite * 60), TimeUnit.HOURS);
    }

    private String redisKey(String sessionId) {
        return SsoConfig.SSO_SESSIONID.concat("#").concat(sessionId);
    }


}
