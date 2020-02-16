package cn.scau.springcloud.controller;

import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.form.UserForm;
import cn.scau.springcloud.domain.vo.UserVO;
import cn.scau.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
    public Object login(@Valid @RequestBody UserForm userForm, Model model) {
        Result<UserVO> result = userService.login(userForm);
        if (!result.isSuccess()) {
            return Result.argsErrResult(result.getMsg());
        }
        model.addAttribute("userInfo", result.getResult());
        return "index";
    }

    @RequestMapping("index")
    public Object index() {
        return "index";
    }

    @RequestMapping("meta")
    public Map<String, Object> meta() {
        return null;
    }

}
