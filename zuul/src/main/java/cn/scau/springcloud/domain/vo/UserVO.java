package cn.scau.springcloud.domain.vo;

import lombok.Data;


/**
 * user's information view object
 * @author vincelin
 */
@Data
public class UserVO {
    private String username;
    private String email;
    private String phone;
    private String score;
    private String status;
}
