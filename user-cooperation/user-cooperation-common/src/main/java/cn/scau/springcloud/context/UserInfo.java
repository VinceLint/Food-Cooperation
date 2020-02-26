package cn.scau.springcloud.context;

import lombok.Data;

@Data
public class UserInfo {
    /** id*/
    private Integer id;

    /** 用户名*/
    private String username;

    /** 邮箱*/
    private String email;

    /** 电话*/
    private String phone;

    /** 0～5，用户评分*/
    private Integer score;
}
