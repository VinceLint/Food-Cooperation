package cn.scau.springcloud.domain.request;

import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegisterReq {
    /** 用户名*/
    @NotEmpty(message = "用户名不能为空")
    @Length(min=6, max = 20, message = "用户名长度必须在{min}到{max}之间")
    private String username;

    /** 密码*/
    @NotEmpty(message = "密码不能为空")
    @Length(min=6, max = 20, message = "密码长度必须在{min}到{max}之间")
    private String password;

    /** 密码盐值*/
    private String salt;

    /** 邮箱*/
    @NotEmpty(message = "邮箱不能为空")
    @Length(min=6, max = 30, message = "邮箱长度必须在{min}到{max}之间")
    private String email;

    /** 0:普通用户 1:运营人员 2:开发人员*/
    private Integer identity;

    /** 电话*/
    @NotEmpty(message = "手机号不能为空")
    @Length(min=11, max = 11, message = "请输入11位手机号")
    private String phone;

    /** 0～5，用户评分*/
    private Integer score;

    /** 0:正常 2:冻结 2:删除*/
    private Integer status;
}
