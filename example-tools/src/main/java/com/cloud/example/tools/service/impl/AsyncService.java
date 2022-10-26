package com.cloud.example.tools.service.impl;

import com.cloud.example.config.Config;
import com.cloud.example.tools.service.IAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/12
 */
@Slf4j
@Service
public class AsyncService implements IAsyncService {

    @Async("asyncExecutor")
    @Override
    public String asyncHungry(int num) {
        log.info("asyncHungry---------2--{}", num);
        try {
            log.info(Config.title);
            log.info(String.valueOf(Config.earlyMails));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("InterruptedException异常", e);
        }
        log.info("asyncHungry---------3--{}", num);
        return "asyncHungry--" + num;
    }

    @Async
    @Override
    public void async() {
        log.info("asyncGet---------2");
        try {
            log.info(Config.title);
            log.info(String.valueOf(Config.earlyMails));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("InterruptedException异常", e);
        }
        log.info("asyncGet---------3");
    }

    @Async
    @Override
    public void asyncException() {
        log.info("asyncGet---------2");
        int i = 1 / 0;
        log.info("asyncGet---------3");
    }

    @Async
    @Override
    public void asyncCatchException() {
        log.info("asyncGet---------2");
        int i = 1 / 0;
        log.info("asyncGet---------3");
    }
}
