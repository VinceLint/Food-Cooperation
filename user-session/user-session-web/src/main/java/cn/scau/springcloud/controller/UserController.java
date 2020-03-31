package cn.scau.springcloud.controller;

import cn.scau.springcloud.context.UserSessionContextHolder;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.UserDO;
import cn.scau.springcloud.domain.request.*;
import cn.scau.springcloud.domain.response.LoginResp;
import cn.scau.springcloud.domain.response.RegisterResp;
import cn.scau.springcloud.domain.vo.ResultVO;
import cn.scau.springcloud.form.UserForm;
import cn.scau.springcloud.handler.ValidatorResultHandler;
import cn.scau.springcloud.manager.UserManager;
import cn.scau.springcloud.util.JwtUtils;
import cn.scau.springcloud.util.StringUtil;
import cn.scau.springcloud.utils.IMailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
    public ResultVO login(@RequestBody @Valid LoginReq loginReq, BindingResult bindingResult) {
        ResultVO validRst = ValidatorResultHandler.handler(bindingResult);
        if (!validRst.isSuccess()) {
            return validRst;
        }
        Result<LoginResp> result = userManager.login(loginReq);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }

    @RequestMapping(value = "getVerifyCode", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO getVerifyCode(@RequestParam(value="username", required = false) String username,
                                  @RequestParam(value="email", required = false) String email) {
        if (StringUtil.isEmpty(username)) {
            return ResultVO.error(-1, "用户名不能为空");
        }
        if (StringUtil.isEmpty(email)) {
            return ResultVO.error(-1, "邮箱不能为空");
        }
        Result<Boolean> result = userManager.getVerifyCode(username, email);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO register(@RequestBody @Valid RegisterReq registerReq, BindingResult bindingResult) {
        ResultVO validRst = ValidatorResultHandler.handler(bindingResult);
        if (!validRst.isSuccess()) {
            return validRst;
        }
        Result<Boolean> result = userManager.register(registerReq);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }

    @RequestMapping(value = "forgetPwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO forgetPwd(@RequestBody @Valid ResetPwdReq resetPwdReq, BindingResult bindingResult) {
        ResultVO validRst = ValidatorResultHandler.handler(bindingResult);
        if (!validRst.isSuccess()) {
            return validRst;
        }
        Result<Boolean> result = userManager.resetPwd(resetPwdReq);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }

    @RequestMapping(value = "changePwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO changePwd(@RequestBody @Valid ChangePwdReq changePwdReq, BindingResult bindingResult) {
        ResultVO validRst = ValidatorResultHandler.handler(bindingResult);
        if (!validRst.isSuccess()) {
            return validRst;
        }
        Result<Boolean> result = userManager.changePwd(changePwdReq);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }

    @RequestMapping(value = "changeMsg", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO changeMsg(@RequestBody @Valid ChangeMsgReq changeMsgReq, BindingResult bindingResult) {
        ResultVO validRst = ValidatorResultHandler.handler(bindingResult);
        if (!validRst.isSuccess()) {
            return validRst;
        }
        Result<Boolean> result = userManager.changeMsg(changeMsgReq);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }

    @RequestMapping(value = "me", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO me() {
        Result<UserDO> result = userManager.getUser(UserSessionContextHolder.getUserId());
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }

    @RequestMapping("getUser")
    @ResponseBody
    public ResultVO getUser(@RequestParam Integer id) {
        Result<UserDO> result = userManager.getUser(id);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }
}

