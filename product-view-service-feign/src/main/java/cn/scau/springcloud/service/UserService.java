package cn.scau.springcloud.service;

import cn.scau.springcloud.client.UserClientFeign;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.form.UserForm;
import cn.scau.springcloud.domain.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vincelin
 * @date 2020/2/16 5:13 PM
 */
@Service
public class UserService {
    @Autowired
    private UserClientFeign userClientFeign;

    public Result<UserVO> login(UserForm userForm){
        return userClientFeign.login(userForm);
    }
}
