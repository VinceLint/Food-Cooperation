package cn.scau.springcloud.domain;

import lombok.Data;

@Data
public class UserInfo {
    private String userName;
    private String password;
    private String email;
}
