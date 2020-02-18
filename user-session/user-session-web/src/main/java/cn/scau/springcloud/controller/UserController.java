package cn.scau.springcloud.controller;

import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.vo.UserVO;
import cn.scau.springcloud.form.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("doLogin")
    public Object login(@RequestBody @Valid UserForm userForm){
        System.out.println("进来了");
        // 校验登陆信息
//        if (true) {
////            存入redis，key=user:admin:info value=admin，过期时间1个小时
//            Jedis jedis = new Jedis("192.168.1.3",6379);
//            jedis.setex("user:admin:info", 3600, "admin");
//            jedis.close();
//
//            //使用jwt生成token
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("username", "admin");
////            return JwtUtil.encode("sso.fg.cn", map, "salt");
//            System.out.println("success");
//        }
        UserVO userVO = new UserVO();
        userVO.setUsername("vincelin");
        return Result.successResult(userVO);
    }
}
