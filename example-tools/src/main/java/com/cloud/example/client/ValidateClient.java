package com.cloud.example.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.BusinessException;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.domain.UserParam;
import com.cloud.example.domain.ValidateGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Slf4j
@RestController
@RequestMapping(value = "/sso")
public class ValidateClient extends BaseClient {


    @ResponseBody
    @RequestMapping(value = "/show")
    public ResultResponse show(@Validated(value = ValidateGroup.GroupA.class) @RequestBody UserParam param,
                               BindingResult result) {
        if (result.hasErrors()) {
            throw new BusinessException(result.getAllErrors().get(0).getDefaultMessage());
        }
        return ResultResponse.success(param);
    }


}
