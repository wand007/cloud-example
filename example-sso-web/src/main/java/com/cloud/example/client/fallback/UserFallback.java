package com.cloud.example.client.fallback;

import com.cloud.example.base.ResultResponse;
import com.cloud.example.client.IUserFeign;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */
@Component
public class UserFallback implements IUserFeign {

    @Override
    public ResultResponse findDetail(@RequestParam(value = "id") String id) {
        return ResultResponse.success("测试异常");
    }
}
