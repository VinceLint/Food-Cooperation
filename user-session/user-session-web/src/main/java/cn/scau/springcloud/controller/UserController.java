package cn.scau.springcloud.controller;

import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.UserDO;
import cn.scau.springcloud.domain.request.LoginReq;
import cn.scau.springcloud.domain.request.RegisterReq;
import cn.scau.springcloud.domain.request.ResetPwdReq;
import cn.scau.springcloud.domain.response.LoginResp;
import cn.scau.springcloud.domain.response.RegisterResp;
import cn.scau.springcloud.domain.vo.ResultVO;
import cn.scau.springcloud.form.UserForm;
import cn.scau.springcloud.manager.UserManager;
import cn.scau.springcloud.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user/")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserManager userManager;

    @RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResultVO login(@RequestBody @Valid LoginReq loginReq) {
        Result<LoginResp> result = userManager.login(loginReq);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO register(@RequestBody @Valid  RegisterReq registerReq) {
        Result<Boolean> result = userManager.register(registerReq);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }

    @RequestMapping(value = "forgetPwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO forgetPwd(@RequestBody @Valid ResetPwdReq resetPwdReq) {
        Result<Boolean> result = userManager.resetPwd(resetPwdReq);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }

    @RequestMapping("getUser")
    @ResponseBody
    public ResultVO getUser(@RequestParam Integer id){
        Result<UserDO> result = userManager.getUser(id);
        if (!result.isSuccess()){
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }
}

