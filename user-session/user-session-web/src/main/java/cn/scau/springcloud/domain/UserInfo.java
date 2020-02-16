package cn.scau.springcloud.domain;

import lombok.Data;

@Data
public class UserInfo {
    private String username;
    private String email;
    private String phone;
    private String score;
    private String status;
}
