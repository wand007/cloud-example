package com.cloud.example.redisson.comm;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/16
 */
@Component
public class DistributedRedisLock {
    @Resource
    private RedissonClient redisson;

    private static final String LOCK_TITLE = "redisLock_";

    public boolean acquire(String lockName) {
        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getFairLock(key);
        try {
            //lock提供带timeout参数，timeout结束强制解锁，防止死锁
            mylock.tryLock(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("锁冲突======lock======" + Thread.currentThread().getName() + "======lockName======" + key);
        }
        System.err.println("======lock======" + Thread.currentThread().getName() + "======lockName======" + key);
        return true;
    }

    public void release(String lockName) {
        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(key);
        mylock.unlock();
        System.err.println("======unlock======" + Thread.currentThread().getName() + "======lockName======" + key);
    }
}
