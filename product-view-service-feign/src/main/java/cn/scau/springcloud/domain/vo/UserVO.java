package cn.scau.springcloud.domain.vo;

import lombok.Data;

@Data
public class UserVO {
    private String username;
    private String email;
    private String phone;
    private String score;
    private String status;
}
