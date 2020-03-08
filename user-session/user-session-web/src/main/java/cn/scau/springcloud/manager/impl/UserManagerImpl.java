package cn.scau.springcloud.manager.impl;

import cn.scau.springcloud.context.UserInfo;
import cn.scau.springcloud.context.UserSessionContextHolder;
import cn.scau.springcloud.dao.UserDao;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.UserDO;
import cn.scau.springcloud.domain.request.*;
import cn.scau.springcloud.domain.response.LoginResp;
import cn.scau.springcloud.enums.IdentityEnums;
import cn.scau.springcloud.enums.StatusEnums;
import cn.scau.springcloud.manager.UserManager;
import cn.scau.springcloud.util.JwtUtils;
import cn.scau.springcloud.util.PasswordUtils;
import cn.scau.springcloud.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
public class UserManagerImpl implements UserManager {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private UserDao userDao;

    @Override
    public Result<LoginResp> login(LoginReq loginReq) {
        Result<UserDO> result = userDao.getUserByUsername(loginReq.getUsername());
        if (!result.isSuccess()) {
            log.warn("can not find the account");
            return Result.argsErrResult("账号不存在");
        }
        UserDO userDO = result.getResult();
        String orgPassword = userDO.getPassword();
        String salt = userDO.getSalt();
        String password = DigestUtils.md5Hex(loginReq.getPassword() + salt);
        if (!orgPassword.equals(password)) {
            return Result.argsErrResult("密码错误");
        }
        // 登陆成功设置token并返回给前端
        String token = JwtUtils.createJWT(loginReq.getUsername());
        System.out.println(token);
        // 在redis中存储2小时
        redisUtils.set(token, result.getResult(), 60 * 60 * 2);
        LoginResp loginResp = new LoginResp();
        loginResp.setToken(token);
        return Result.successResult(loginResp);
    }

    @Override
    public Result<Boolean> register(RegisterReq registerReq) {
        Result<UserDO> result = userDao.getUserByUsername(registerReq.getUsername());
        if (result.hasSuccessValue()) {
            return Result.argsErrResult("该用户名已存在");
        }
        Map<String, String> encodePwd = PasswordUtils.generateNewPassword(registerReq.getPassword());
        UserDO userDO = new UserDO();
        userDO.setUsername(registerReq.getUsername());
        userDO.setPassword(encodePwd.get("md5Pwd"));
        userDO.setSalt(encodePwd.get("salt"));
        userDO.setEmail(registerReq.getEmail());
        userDO.setIdentity(IdentityEnums.NORMAL.getType());
        userDO.setPhone(registerReq.getPhone());
        userDO.setStatus(StatusEnums.NORMAL.getType());
        userDO.setScore(5.0f);
        Result<UserDO> userDOResult = userDao.insert(userDO);
        if (!userDOResult.isSuccess()) {
            return Result.sysErrResult(userDOResult.getMsg());
        }
        return Result.successResult(true);
    }

    @Override
    public Result<Boolean> resetPwd(ResetPwdReq resetPwdReq) {
        Result<UserDO> result = userDao.getUserByUsername(resetPwdReq.getUsername());
        if (!result.hasSuccessValue()) {
            return Result.argsErrResult("该用户不存在");
        }
        UserDO userDO = result.getResult();
        if (!userDO.getEmail().equals(resetPwdReq.getEmail())) {
            return Result.argsErrResult("该用户绑定的邮箱有误");
        }
        if (!resetPwdReq.getNewPassword().equals(resetPwdReq.getConfirmPassword())) {
            return Result.argsErrResult("两次输入的密码不一致");
        }
        String salt = userDO.getSalt();
        String newMD5Password = PasswordUtils.generateMd5Pwd(resetPwdReq.getNewPassword(), salt);
        userDO.setPassword(newMD5Password);
        Result<UserDO> userDOResult = userDao.update(userDO);
        if (!userDOResult.isSuccess()) {
            return Result.errResult(userDOResult.getCode(), userDOResult.getMsg());
        }
        return Result.successResult(true);

    }

    @Override
    public Result<UserDO> getUser(Integer id) {
        if (id == null || id <= 0) {
            return Result.argsErrResult();
        }
        Result<UserDO> result = userDao.getUserById(id);
        if (!result.isSuccess()) {
            return Result.errResult(result.getCode(), result.getMsg());
        }
        return Result.successResult(result.getResult());
    }

    @Override
    public Result<Boolean> changePwd(ChangePwdReq changePwdReq) {
        Integer id = UserSessionContextHolder.getUserId();
        System.out.println("cp" + id);
        if (id == null || id <= 0) {
            return Result.argsErrResult("登陆信息失效，请重新登陆");
        }
        Result<UserDO> result = userDao.getUserById(id);
        if (!result.hasSuccessValue()) {
            return Result.errResult(result.getCode(), result.getMsg());
        }
        UserDO userDO = result.getResult();
        String orgPassword = userDO.getPassword();
        String salt = userDO.getSalt();
        String oldPassword = PasswordUtils.generateMd5Pwd(changePwdReq.getOldPassword(), salt);
        if (!orgPassword.equals(oldPassword)) {
            return Result.argsErrResult("原密码错误！");
        }
        if (changePwdReq.getNewPassword().equals(changePwdReq.getOldPassword())) {
            return Result.argsErrResult("新密码不能和原密码一样！");
        }
        if (!changePwdReq.getNewPassword().equals(changePwdReq.getConfirmPassword())) {
            return Result.argsErrResult("新密码和确认密码不一致！");
        }
        String newPassword = PasswordUtils.generateMd5Pwd(changePwdReq.getNewPassword(), salt);
        userDO.setPassword(newPassword);
        Result<UserDO> userDOResult = userDao.update(userDO);
        if (!userDOResult.isSuccess()) {
            return Result.errResult(userDOResult.getCode(), userDOResult.getMsg());
        }
        return Result.successResult(true);
    }

    @Override
    public Result<Boolean> changeMsg(ChangeMsgReq changeMsgReq) {
        UserDO userDO = new UserDO();
        userDO.setId(UserSessionContextHolder.getUserId());
        userDO.setEmail(changeMsgReq.getEmail());
        userDO.setPhone(changeMsgReq.getPhone());
        Result<UserDO> userDOResult = userDao.update(userDO);
        if (!userDOResult.isSuccess()) {
            return Result.errResult(userDOResult.getCode(), userDOResult.getMsg());
        }
        return Result.successResult(true);
    }
}
