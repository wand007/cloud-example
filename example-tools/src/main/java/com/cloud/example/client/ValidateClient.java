package com.cloud.example.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.BusinessException;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.domain.UserParam;
import com.cloud.example.domain.ValidateGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Slf4j
@Validated
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

    @ResponseBody
    @RequestMapping(value = "/getCityId", method = RequestMethod.GET)
    public ResultResponse getCityId(
            @NotNull(message = "lat不能为空") BigDecimal lat,
            @NotNull(message = "lng不能为空") BigDecimal lng) {
        log.info("lat:" + lat + ",lng:" + lng);
        return ResultResponse.success();
    }
}
