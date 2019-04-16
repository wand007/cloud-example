package com.cloud.example.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.comm.DistributedRedisLock;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Slf4j
@RestController
@RequestMapping(value = "")
public class RedissonController extends BaseClient {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private DistributedRedisLock distributedRedisLock;


    @ResponseBody
    @RequestMapping(value = "/get1")
    public ResultResponse get() {
        int size = redissonClient.getMap("TEST:").size();
        return ResultResponse.success(size);
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public ResultResponse add() {
        redissonClient.getMap("TEST:").put("TEST","32");
        return ResultResponse.success();
    }

    @RequestMapping("/redder")
    @ResponseBody
    public ResultResponse redder() {
        String key = "test123";
        //加锁
        distributedRedisLock.acquire(key);
        //执行具体业务逻辑

        //释放锁
        distributedRedisLock.release(key);
        //返回结果
        return ResultResponse.success();
    }

    @RequestMapping("/redisLock")
    @ResponseBody
    private void redisLock() {
        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String key = "test123";
                        distributedRedisLock.acquire(key);
                        Thread.sleep(1000); //获得锁之后可以进行相应的处理
                        System.err.println("======获得锁后进行相应的操作======"+ finalI);
                        distributedRedisLock.release(key);
                        System.err.println("=============================");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
    }


}
