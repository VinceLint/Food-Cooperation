package cn.scau.springcloud.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserForm {
    /**
     * 用户名
     */
    @NotEmpty(message = "请输入用户名")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "请输入密码")
    private String password;

//    /**
//     * 邮箱
//     */
//    @NotEmpty(message = "请输出邮箱")
//    private String eamil;
}
