package com.cloud.example.tools.client;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author 咚咚锵
 * @date 2021/5/26 下午7:30
 * @description SecureRandom.getInstanceStrong()引发的线程阻塞问题分析
 * <p>
 * https://blog.csdn.net/weixin_45244678/article/details/106137948?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-6.baidujs&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-6.baidujs
 */
public class TestRandomClient {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("start.....");
        long start = System.currentTimeMillis();
        SecureRandom random = SecureRandom.getInstanceStrong();

        for (int i = 0; i < 100; i++) {
            System.out.println("第" + i + "个随机数.");
            random.nextInt(10000);
        }
        System.out.println("finish...time/ms:" + (System.currentTimeMillis() - start));
    }
}
