package cn.scau.springcloud.domain.response;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Data
@ToString(exclude = {"password"})
public class LoginResp {
    /**
     * 状态
     */
    private Integer status;

    /**
     * token
     */
    private String token;
}
