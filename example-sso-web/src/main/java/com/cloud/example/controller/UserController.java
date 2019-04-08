package com.cloud.example.controller;

import com.cloud.example.base.BaseController;
import com.cloud.example.base.BusinessException;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.client.IUserFeign;
import com.cloud.example.domain.UserParam;
import com.cloud.example.domain.ValidateGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController extends BaseController {


    @Autowired
    IUserFeign iUserFeign;

    @ResponseBody
    @RequestMapping(value = "/findDetail")
    public ResultResponse findDetail(String id) {
        return iUserFeign.findDetail(id);
    }


    @ResponseBody
    @RequestMapping(value = "/add")
    public ResultResponse add(@Validated(value = ValidateGroup.GroupB.class) @RequestBody UserParam param, BindingResult result) {
        if (result.hasErrors()) {
            throw new BusinessException(result.getAllErrors().get(0).getDefaultMessage());
        }
        return iUserFeign.add(param);
    }
}
