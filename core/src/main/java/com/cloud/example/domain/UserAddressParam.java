package com.cloud.example.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-05-13
 */
@Data
public class UserAddressParam {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 收货人姓名
     */
    @NotNull(message = "收货人姓名不能为空", groups = {ValidateGroup.GroupA.class})
    private String name;
    /**
     * 收货人手机号
     */
    @Length(min = 11, max = 11)
    @NotNull(message = "收货人手机号不能为空", groups = {ValidateGroup.GroupA.class})
    private String mobile;
}
