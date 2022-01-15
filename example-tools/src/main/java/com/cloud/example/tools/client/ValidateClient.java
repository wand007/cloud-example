package com.cloud.example.tools.client;

import com.cloud.example.base.BaseClient;
import com.cloud.example.base.BusinessException;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.domain.UserParam;
import com.cloud.example.domain.ValidateGroup;
import com.cloud.example.tools.vo.BatchParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/valid")
public class ValidateClient extends BaseClient {


    @ResponseBody
    @RequestMapping(value = "/test1")
    public ResultResponse test1(@Validated(value = ValidateGroup.GroupA.class) @RequestBody UserParam param,
                                BindingResult result) {
        if (result.hasErrors()) {
            throw new BusinessException(result.getAllErrors().get(0).getDefaultMessage());
        }
        return ResultResponse.success(param);
    }

    @ResponseBody
    @PostMapping(value = "/test2")
    public ResultResponse test2(@RequestParam(name = "province", required = false) String province,
                                @RequestParam(name = "city", required = false) String city,
                                @RequestParam(name = "sellFlag", required = false) Boolean sellFlag,
                                @NotNull(message = "上调比例不能为空")
                                @DecimalMin(value = "0", message = "上调比例最小为0")
                                @DecimalMax(value = "200", message = "上调比例最大为200")
                                @RequestParam(name = "upScale") BigDecimal upScale) {
        return ResultResponse.success(province + city + sellFlag);
    }


    /**
     * 批量保存
     *
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/test3", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultResponse test3(@NotEmpty(message = "请至少选择一个商品")
                                @Size(max = 300, message = "最多可选300条记录")
                                @Valid @RequestBody List<BatchParam> params) {
        return ResultResponse.success(params);
    }
}
