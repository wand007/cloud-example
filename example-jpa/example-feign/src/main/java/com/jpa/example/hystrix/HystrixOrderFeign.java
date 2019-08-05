package com.jpa.example.hystrix;

import com.cloud.example.base.BusinessCode;
import com.cloud.example.base.ResultResponse;
import com.jpa.example.feign.FeignOrder;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-08-05
 */
@Component
public class HystrixOrderFeign implements FallbackFactory<FeignOrder> {
    static Logger logger = LoggerFactory.getLogger(HystrixOrderFeign.class);

    @Override
    public FeignOrder create(Throwable throwable) {
        logger.error("错误信息：", throwable);
        return new FeignOrder() {
            @Override
            public ResultResponse findDetail(String id) {
                return ResultResponse.fromBusinessCode(BusinessCode.ERROR);
            }
        };
    }
}
