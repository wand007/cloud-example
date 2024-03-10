package com.cloud.example.client;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-04-18
 */


@Slf4j
public class ThreadClient {

    public static void main(String[] args) {
        deadLock();
    }

    /**
     * 死锁
     */
    public static void deadLock() {

        new Thread(() -> {
            synchronized (String.class) {
                synchronized (Integer.class) {

                }
            }
        }, "t1").start();


        new Thread(() -> {
            synchronized (Integer.class) {

                synchronized (String.class) {

                }
            }
        }, "t2").start();
    }
}
