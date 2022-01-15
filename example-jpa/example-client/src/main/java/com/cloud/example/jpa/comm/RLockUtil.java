package com.cloud.example.jpa.comm;

import com.cloud.example.enums.ResCodeEnum;
import com.cloud.example.jpa.exception.JpaException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Redission分布式锁封装工具类
 *
 * @author leijingshuo 2020/12/25
 */
@SuppressWarnings({"unused", "AlibabaLowerCamelCaseVariableNaming"})
@Slf4j
@Component
public class RLockUtil {
    @Resource
    private RedissonClient redisson;

    /**
     * 使用Redission分布式锁
     *
     * @param lockKey      锁标识
     * @param waitSeconds  等待锁时间（秒）
     * @param leaseSeconds 锁的超时时间（秒）
     * @param task         处理任务
     * @return T
     */
    public <T> T useRLock(String lockKey, long waitSeconds, long leaseSeconds, Callable<T> task) {
        this.validateParams(lockKey, waitSeconds, leaseSeconds, task);
        RLock lock = redisson.getLock(lockKey);
        try {
            boolean lockFlag = lock.tryLock(waitSeconds, leaseSeconds, TimeUnit.SECONDS);
            if (lockFlag) {
                T result = task.call();
                //获取锁的剩余时间
                long remainTime = lock.remainTimeToLive();
                if (remainTime < 0) {
                    /*
                      如果锁的剩余时间为负数，说明锁早就过期了，这个时候其他线程可能早就持有锁了，
                      这个时候如果再提交事务，就会出现并发问题
                      此时应该直接回滚事务
                     */
                    throw new JpaException(ResCodeEnum.LOCK_FAILED.getCode(), "业务处理超时，请稍候重试！");
                }
                return result;
            } else {
                throw new JpaException(ResCodeEnum.LOCK_FAILED.getCode(), "服务繁忙，请稍候重试！");
            }
        } catch (Exception e) {
            log.error("分布式加锁失败 lockKey:{}", lockKey, e);
            throw new JpaException(ResCodeEnum.LOCK_FAILED.getCode(), e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private <T> void validateParams(String lockKey, long waitSeconds, long leaseSeconds, Callable<T> task) {
        if (StringUtils.isBlank(lockKey)) {
            throw new JpaException(ResCodeEnum.LOCK_FAILED.getCode(), "锁标识不能为空");
        }
        if (waitSeconds <= 0 || leaseSeconds <= 0) {
            throw new JpaException(ResCodeEnum.LOCK_FAILED.getCode(), "等待锁时间和锁的超时时间不等小于等于0！");
        }
        if (task == null) {
            throw new JpaException(ResCodeEnum.LOCK_FAILED.getCode(), "处理任务不能为空");
        }
    }
}
