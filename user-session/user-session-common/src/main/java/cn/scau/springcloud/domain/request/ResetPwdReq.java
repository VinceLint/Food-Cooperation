package cn.scau.springcloud.domain.request;

import lombok.Data;

@Data
public class ResetPwdReq {
    private String username;
    private String email;
    private String newPassword;
    private String confirmPassword;
}
