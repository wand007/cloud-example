package com.cloud.example.service;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/12
 */
public interface IAsyncService {
    void async();


    void asyncException();

    void asyncCatchException();

}
