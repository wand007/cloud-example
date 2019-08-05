package com.jpa.example.jpa.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Data
@Entity
@Table(name = "tbl_order")
public class OrderDAO implements Serializable {

    private static final long serialVersionUID = 2986776501058142454L;
    @Id
    @Column(name = "id", columnDefinition = "varchar(32) COMMENT '订单ID'")
    private String id;

    @Column(name = "user_id", columnDefinition = "varchar(64) COMMENT '用户ID'")
    private String userId;

    @Column(name = "resource_id", columnDefinition = "varchar(64) COMMENT '资源id'")
    private String resourceId;

    @Column(name = "resource_name", columnDefinition = "varchar(64) COMMENT '资源名称'")
    private String resourceName;

    @Column(name = "resource_logo", columnDefinition = "varchar(64) COMMENT '资源logo'")
    private String resourceLogo;
    /**
     * com.cloud.example.enums.OrderStatusEnum
     */
    @Column(name = "status", columnDefinition = "tinyint COMMENT '订单状态'")
    private Integer status;

    @Column(name = "quantity", columnDefinition = "int COMMENT '数量'")
    private Integer quantity;

    @CreationTimestamp
    @Column(name = "create_date")
    public Date createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    public Date updateDate;
}
