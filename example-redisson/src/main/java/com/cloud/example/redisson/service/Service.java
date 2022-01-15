package com.cloud.example.redisson.service;

import com.cloud.example.redisson.comm.DistributedRedisLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/16
 */
@Component
public class Service extends Thread {

    @Resource
    private DistributedRedisLock distributedRedisLock;


    int n = 500;


    // 模拟线程进行秒杀服务

    @Override
    public void run() {
        // 返回锁的value值，供释放锁时候进行判断
        distributedRedisLock.acquire("resource");
        System.out.println(Thread.currentThread().getName() + "获得了锁");
        System.out.println(--n);
        distributedRedisLock.release("resource");
    }


}


