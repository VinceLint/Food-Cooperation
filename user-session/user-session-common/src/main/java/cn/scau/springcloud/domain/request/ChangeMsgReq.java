package cn.scau.springcloud.domain.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class ChangeMsgReq {
    /** 邮箱*/
    @NotEmpty(message = "邮箱不能为空")
    @Length(min=6, max = 30, message = "邮箱长度必须在{min}到{max}之间")
    private String email;

    /** 电话*/
    @NotEmpty(message = "手机号不能为空")
    @Length(min=11, max = 11, message = "请输入11位手机号")
    private String phone;
}
