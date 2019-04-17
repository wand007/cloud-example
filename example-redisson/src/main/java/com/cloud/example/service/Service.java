package com.cloud.example.service;

import com.cloud.example.comm.DistributedRedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/16
 */
@Component
public class Service extends Thread {

    @Autowired
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


