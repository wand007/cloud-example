package com.cloud.example.jpa.strategy.one;

import com.alibaba.fastjson.JSON;
import com.cloud.example.jpa.param.req.OrderCreateReq;
import com.cloud.example.jpa.strategy.UserCheckStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 咚咚锵
 * @date 2022/1/15 下午2:33
 * @description TODO
 */
@Slf4j
@Component("ONE_STRATEGY")
public class UserCheckOneStrategy extends UserCheckStrategy {
    @Override
    public void checkRisk(OrderCreateReq req) {
        log.warn("风险一就是打印个日志 ONE_STRATEGY :{}", JSON.toJSONString(req));
    }
}
