package cn.scau.springcloud.service;


import cn.scau.springcloud.dao.UserDao;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.dto.UserDTO;
import cn.scau.springcloud.domain.entity.UserDO;
import cn.scau.springcloud.manager.UserManager;
import cn.scau.springcloud.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 对外api
 *
 * @author
 * @date 2020/3/7 5:24 PM
 */
@RestController()
public class UserService {

    @Resource
    private UserDao userDao;

    @RequestMapping(value = "/user-api/getUser", method = RequestMethod.GET)
    public UserDTO getUserById(@RequestParam("id") Integer id) {
        Result<UserDO> result = userDao.getUserById(id);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(result.getResult(), userDTO);
        return userDTO;
    }

    @RequestMapping(value = "/user-api/syncScore", method = RequestMethod.GET)
    public void syncScore(@RequestParam("id") Integer id, @RequestParam("score") Float score) {
        System.out.println(id + " " + score);
        Result<UserDO> result = userDao.getUserById(id);
        UserDO userDO = result.getResult();
        if (userDO != null) {
            userDO.setScore(score);
            userDao.update(userDO);
        }
    }
}
