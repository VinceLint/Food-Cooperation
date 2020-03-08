package cn.scau.springcloud.service;


import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.dto.UserDTO;
import cn.scau.springcloud.domain.entity.UserDO;
import cn.scau.springcloud.manager.UserManager;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 对外api
 * @author
 * @date 2020/3/7 5:24 PM
 */
@RestController()
public class UserService {

    @Resource
    private UserManager userManager;

    @RequestMapping(value = "/user-api/getUser", method = RequestMethod.GET)
    public UserDTO getUserById(@RequestParam("id")Integer id) {
        Result<UserDO> result = userManager.getUser(id);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(result.getResult(), userDTO);
        return userDTO;
    }
}
