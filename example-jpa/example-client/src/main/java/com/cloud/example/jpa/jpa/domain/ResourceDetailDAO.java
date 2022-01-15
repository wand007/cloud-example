package com.cloud.example.jpa.jpa.domain;

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
 * @Description 资源详情表
 * @Date 2019/1/7
 */
@Data
@Entity
@Table(name = "tbl_resource_detail")
public class ResourceDetailDAO implements Serializable {

    private static final long serialVersionUID = 1421182082368455221L;
    @Id
    @Column(name = "id", columnDefinition = "varchar(32) COMMENT '主键'")
    private String id;

    @Column(name = "resource_desc", columnDefinition = "TEXT COMMENT '资源描述  考虑字段长度' ")
    private String resourceDesc;

    @Column(name = "resource_pic_url", columnDefinition = "TEXT COMMENT '图片格式化的字符串  考虑字段长度' ")
    private String resourcePicUrl;

    @Column(name = "top_class_id", columnDefinition = "int  COMMENT '类目ID'")
    private Integer topClassId;

    @CreationTimestamp
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Date updateDate;


}
