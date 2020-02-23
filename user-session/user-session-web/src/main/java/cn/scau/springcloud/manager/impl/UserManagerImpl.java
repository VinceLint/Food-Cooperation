package cn.scau.springcloud.manager.impl;

import cn.scau.springcloud.dao.UserDao;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.UserDO;
import cn.scau.springcloud.domain.request.LoginReq;
import cn.scau.springcloud.domain.request.RegisterReq;
import cn.scau.springcloud.domain.request.ResetPwdReq;
import cn.scau.springcloud.domain.response.LoginResp;
import cn.scau.springcloud.domain.response.RegisterResp;
import cn.scau.springcloud.enums.IdentityEnums;
import cn.scau.springcloud.enums.StatusEnums;
import cn.scau.springcloud.manager.UserManager;
import cn.scau.springcloud.util.JwtUtils;
import cn.scau.springcloud.util.PasswordUtils;
import cn.scau.springcloud.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        userDO.setScore(5);
        Result<UserDO> userDOResult = userDao.insert(userDO);
        if (!userDOResult.isSuccess()) {
            return Result.sysErrResult();
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
        if (userDO.getEmail() != resetPwdReq.getEmail()) {
            return Result.argsErrResult("该用户绑定的邮箱有误");
        }
        if (!resetPwdReq.getNewPassword().equals(resetPwdReq.getConfirmPassword())){
            return Result.argsErrResult("两次输入的密码不一致");
        }
        String salt = userDO.getSalt();
        String newMD5Password = PasswordUtils.generateMd5Pwd(resetPwdReq.getNewPassword(), salt);
        userDO.setPassword(newMD5Password);
        Result<UserDO> userDOResult = userDao.update(userDO);
        if (!userDOResult.isSuccess()){
            return Result.sysErrResult();
        }
        return Result.successResult(true);

    }
}
