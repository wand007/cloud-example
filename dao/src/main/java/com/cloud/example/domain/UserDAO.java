package com.cloud.example.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Data
@Entity
@Table(name = "tbl_user")
public class UserDAO {

    @Id
    @Column(name = "id", columnDefinition = "varchar(32) COMMENT '用户id'")
    private String id;

    @Column(name = "avatar_url", columnDefinition = "varchar(32) COMMENT '头像'")
    private String avatarUrl;

    @Column(name = "nick_name", columnDefinition = "varchar(32) COMMENT '用户昵称'")
    private String nickName;

    @Column(name = "phone_no", columnDefinition = "varchar(32) COMMENT '手机号'")
    private String phoneNo;

    @Column(name = "password", columnDefinition = "varchar(32) COMMENT '登陆密码'")
    private String password;

    @CreationTimestamp
    @Column(name = "create_date")
    public Date createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    public Date updateDate;
}
