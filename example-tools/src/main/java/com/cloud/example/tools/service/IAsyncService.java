package com.cloud.example.tools.service;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/12
 */
public interface IAsyncService {

    String asyncHungry(int num);

    void async();


    void asyncException();

    void asyncCatchException();

}
