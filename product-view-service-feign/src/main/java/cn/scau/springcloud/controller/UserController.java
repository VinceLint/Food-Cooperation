package cn.scau.springcloud.controller;

import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.constant.ResultCode;
import cn.scau.springcloud.domain.vo.ResultVO;
import cn.scau.springcloud.form.UserForm;
import cn.scau.springcloud.domain.vo.UserVO;
import cn.scau.springcloud.helper.CookieHelper;
import cn.scau.springcloud.service.UserService;
import cn.scau.springcloud.util.JwtUtils;
import cn.scau.springcloud.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 林跃涛
 * @version 1.0
 * @date 2019/9/17 20:00
 */
@Controller
//@RefreshScope
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResultVO login(@Valid @RequestBody UserForm userForm, HttpServletResponse response) {
        Result<UserVO> result = userService.login(userForm);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        // 登陆成功设置token并返回给前端
        String token = JwtUtils.createJWT(userForm.getUsername());
        redisUtils.set(token, result.getResult(), 60 * 60 * 2);
        System.out.println(token);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        return ResultVO.success();
    }

    @RequestMapping("toLogin")
//    @ResponseBody
    public String toLogin() {
        return "login";
    }

    @RequestMapping("index")
    @ResponseBody
    public ResultVO index(HttpServletRequest request) {
        String token = CookieHelper.getToken(request.getCookies());
        if (token == null) {
            return ResultVO.error(ResultCode.ResponseCode.FORBIDDEN,"您还没有登陆，请登陆");
        }
        UserVO userVO = (UserVO)redisUtils.get(token);
        if (userVO==null){
            return ResultVO.error(ResultCode.ResponseCode.FORBIDDEN, "长时间未操作，登陆信息失效");
        }
        return ResultVO.success(userVO);

    }

    @RequestMapping("meta")
    public Map<String, Object> meta() {
        Map<String, Object> map = new HashMap<>(1);
//        map.put("userInfo", getCurrentUser)
        return null;
    }

}
