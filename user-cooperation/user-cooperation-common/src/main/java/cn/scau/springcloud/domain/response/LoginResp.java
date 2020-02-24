package cn.scau.springcloud.domain.response;

import lombok.Data;
import lombok.ToString;

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
