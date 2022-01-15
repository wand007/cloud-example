package com.cloud.example.redisson.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.redisson.comm.DistributedRedisLock;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Slf4j
@RestController
@RequestMapping(value = "redisson")
public class RedissonController extends BaseClient {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private DistributedRedisLock distributedRedisLock;


    String lockKey = "testRedisson";//分布式锁的key

    @ResponseBody
    @RequestMapping(value = "/get1")
    public ResultResponse get() {
        int size = redissonClient.getMap(lockKey).size();
        return ResultResponse.success(size);
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public ResultResponse add() {
        redissonClient.getMap(lockKey).put("TEST", 110);
        return ResultResponse.success();
    }

    @RequestMapping("/redisLock1")
    @ResponseBody
    public ResultResponse redisLock1() {
        //执行的业务代码
        for (int i = 0; i < 55; i++) {
            distributedRedisLock.acquire(lockKey);
            int stock = Integer.parseInt(redissonClient.getMap(lockKey).get("TEST").toString());
            redissonClient.getMap(lockKey).put("TEST", (stock - 1));
            System.out.println("test2_:lockkey:" + lockKey + ",stock:" + (stock - 1) + "");
            distributedRedisLock.release(lockKey);
        }
        //返回结果
        return ResultResponse.success();
    }

    @RequestMapping("/redisLock2")
    @ResponseBody
    public ResultResponse redisLock2() {
        //执行的业务代码
        for (int i = 0; i < 55; i++) {
            distributedRedisLock.acquire(lockKey);
            int stock = Integer.parseInt(redissonClient.getMap(lockKey).get("TEST").toString());
            redissonClient.getMap(lockKey).put("TEST", (stock - 1));
            System.out.println("test2_:lockkey:" + lockKey + ",stock:" + (stock - 1) + "");
            distributedRedisLock.release(lockKey);
        }
        //返回结果
        return ResultResponse.success();
    }


    @RequestMapping(value = "/index")
    @ResponseBody
    public ResultResponse index(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            session.setAttribute("user", "尼古拉斯蛋蛋");
            System.out.println("不存在session");
        } else {
            System.out.println("存在session");
        }
        //返回结果
        return ResultResponse.success();
    }

}
