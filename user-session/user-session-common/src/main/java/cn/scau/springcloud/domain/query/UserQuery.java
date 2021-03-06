package cn.scau.springcloud.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserQuery extends Query implements Serializable {
    /** id*/
    private Integer id;

    /** 用户名*/
    private String username;

    /** 密码*/
    private String password;

    /** 密码盐值*/
    private String salt;

    /** 邮箱*/
    private String email;

    /** 0:普通用户 1:运营人员 2:开发人员*/
    private Integer identity;

    /** 电话*/
    private String phone;

    /** 0～5，用户评分*/
    private Integer score;

    /** 0:正常 2:冻结 2:删除*/
    private Integer status;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private Integer isDeleted;
}
