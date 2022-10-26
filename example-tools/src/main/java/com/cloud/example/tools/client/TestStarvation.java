package com.cloud.example.tools.client;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author 咚咚锵
 * @date 2022/10/24 下午9:00
 * @description TODO
 */
@Slf4j(topic = "c.TestDeadLock")
public class TestStarvation {

    static final List<String> MENU = Arrays.asList("地三鲜", "宫保鸡丁", "辣子鸡丁", "烤鸡翅");
    static Random RANDOM = new Random();
    // 随机返回一个菜
    static String cooking() {
        return MENU.get(RANDOM.nextInt(MENU.size()));
    }

    public static void main(String[] args) {
        //固定大小为2的线程池
        ExecutorService waiterPool = Executors.newFixedThreadPool(2);

        waiterPool.execute(() -> {
            log.debug("处理点餐...");

            //具体的做菜交与另一线程去做
            Future<String> f = waiterPool.submit(() -> {
                log.debug("做菜");
                return cooking();
            });

            try {
                log.debug("上菜: {}", f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        waiterPool.execute(() -> {
            log.debug("处理点餐...");
            Future<String> f = waiterPool.submit(() -> {
                log.debug("做菜");
                return cooking();
            });
            try {
                log.debug("上菜: {}", f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

    }
}
