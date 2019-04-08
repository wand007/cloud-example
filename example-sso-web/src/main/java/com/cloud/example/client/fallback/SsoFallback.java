package com.cloud.example.client.fallback;

import com.cloud.example.base.ResultResponse;
import com.cloud.example.client.ISsoFeign;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */
@Component
public class SsoFallback implements ISsoFeign {


    @Override
    public ResultResponse checkPassword(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        return ResultResponse.success("测试查询异常");
    }

}
