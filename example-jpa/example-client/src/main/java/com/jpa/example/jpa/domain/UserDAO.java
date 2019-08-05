package com.jpa.example.jpa.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.format.annotation.DateTimeFormat;

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

//    @Transient //此字段不与数据库关联
    @Version//此字段加上乐观锁
    @Column(name = "password", columnDefinition = "varchar(32) COMMENT '登陆密码'")
    private String password;

    @CreationTimestamp
    @Column(name = "create_date")
    public Date createDate;

    @UpdateTimestamp
    //出参时间格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    //入参时，请求报文只需要传入yyyymmddhhmmss字符串进来，则自动转换为Date类型数据
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "update_date")
    public Date updateDate;
}
