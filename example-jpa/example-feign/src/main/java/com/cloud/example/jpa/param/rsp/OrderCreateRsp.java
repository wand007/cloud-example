package com.cloud.example.jpa.param.rsp;

import lombok.Data;

/**
 * @author 咚咚锵
 * @date 2022/1/15 下午1:47
 * @description 订单创建参数
 */
@Data
public class OrderCreateRsp {
    /**
     * 订单ID
     */
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 资源id
     */
    private String resourceId;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 资源logo
     */
    private String resourceLogo;
    /**
     * 数量
     */
    private Integer quantity;
}
