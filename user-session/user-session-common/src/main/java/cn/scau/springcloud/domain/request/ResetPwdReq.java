package cn.scau.springcloud.domain.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class ResetPwdReq {
    /** 用户名*/
    @NotEmpty(message = "用户名不能为空")
    @Length(min=6, max = 20, message = "用户名长度必须在{min}到{max}之间")
    private String username;

    /** 邮箱*/
    @NotEmpty(message = "邮箱不能为空")
    @Length(min=6, max = 30, message = "邮箱长度必须在{min}到{max}之间")
    private String email;

    /** 新密码*/
    @NotEmpty(message = "新密码不能为空")
    @Length(min=6, max = 20, message = "邮箱长度必须在{min}到{max}之间")
    private String newPassword;

    /** 确认密码*/
    @NotEmpty(message = "新密码不能为空")
    @Length(min=6, max = 20, message = "邮箱长度必须在{min}到{max}之间")
    private String confirmPassword;
}
