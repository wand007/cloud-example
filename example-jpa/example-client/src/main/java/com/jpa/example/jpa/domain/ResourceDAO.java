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
@Table(name = "tbl_resource")
public class ResourceDAO implements Serializable{

    private static final long serialVersionUID = 1453728169842484306L;

    @Id
    @Column(name = "id", columnDefinition = "varchar(32) COMMENT '资源ID'")
    private String id;

    @Column(name = "resource_name", columnDefinition = "varchar(64) COMMENT '资源名称'")
    private String resourceName;

    @Column(name = "resource_logo", columnDefinition = "varchar(64) COMMENT '资源logo'")
    private String resourceLogo;

    @Column(name = "category_id", columnDefinition = "int COMMENT '父类别ID'")
    private Integer categoryId;

    @CreationTimestamp
    @Column(name = "create_date")
    public Date createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    public Date updateDate;
}
