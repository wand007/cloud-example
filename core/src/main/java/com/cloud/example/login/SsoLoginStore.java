package com.cloud.example.login;

import com.cloud.example.config.SsoConfig;
import com.cloud.example.domain.sso.SsoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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
     * @param xxlUser
     */
    public void put(String storeKey, SsoUser xxlUser) {
        String redisKey = redisKey(storeKey);
        redisTemplate.opsForHash().put(redisKey, xxlUser, redisExpireMinite * 60);  // minite to second
    }

    private String redisKey(String sessionId) {
        return SsoConfig.SSO_SESSIONID.concat("#").concat(sessionId);
    }

}
