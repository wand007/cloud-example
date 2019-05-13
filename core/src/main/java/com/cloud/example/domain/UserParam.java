package com.cloud.example.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/8
 */
@Data
public class UserParam implements Serializable {

    private static final long serialVersionUID = -4749088757562421191L;
    /**
     * 用户id
     */
    private String id;

    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空", groups = {ValidateGroup.GroupA.class})
    private String avatarUrl;

    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空", groups = {ValidateGroup.GroupA.class})
    @Length(min = 1, max = 20, message = "昵称必须在1-20个字符内", groups = {ValidateGroup.GroupA.class})
    private String nickName;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空", groups = {ValidateGroup.GroupA.class})
    @Pattern(regexp="^((13[0-9])|(15[^4,\\\\D])|(17[0-9])|(18[0-9]))\\\\d{8}$",message="手机号不正确")
    private String phoneNo;

    @Valid
    @NotEmpty(message = "地址不能为空", groups = {ValidateGroup.GroupA.class, ValidateGroup.GroupB.class})
    private UserAddressParam userAddressParam;

    /**
     * 登陆密码
     */
    private String password;

    public Date createDate;

    public Date updateDate;
}
