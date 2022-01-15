package com.cloud.example.jpa.param.req;

import lombok.Data;

/**
 * @author 咚咚锵
 * @date 2022/1/15 下午1:47
 * @description 订单创建参数
 */
@Data
public class OrderCreateReq {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 来源
     */
    private String source;
}
