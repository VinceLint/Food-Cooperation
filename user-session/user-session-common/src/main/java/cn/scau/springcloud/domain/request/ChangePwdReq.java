package cn.scau.springcloud.domain.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class ChangePwdReq {

    /** 原密码*/
    @NotEmpty(message = "原密码不能为空")
    @Length(min=6, max = 20, message = "原密码长度必须在{min}到{max}之间")
    private String oldPassword;

    /** 新密码*/
    @NotEmpty(message = "确认密码不能为空")
    @Length(min=6, max = 20, message = "新密码长度必须在{min}到{max}之间")
    private String newPassword;

    /** 确认密码*/
    @NotEmpty(message = "确认密码不能为空")
    @Length(min=6, max = 20, message = "确认密码长度必须在{min}到{max}之间")
    private String confirmPassword;
}
