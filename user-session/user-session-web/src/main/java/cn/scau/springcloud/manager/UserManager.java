package cn.scau.springcloud.manager;

import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.dto.UserDTO;
import cn.scau.springcloud.domain.entity.UserDO;
import cn.scau.springcloud.domain.request.*;
import cn.scau.springcloud.domain.response.LoginResp;
import cn.scau.springcloud.domain.response.RegisterResp;

public interface UserManager {
    Result<LoginResp> login(LoginReq loginReq);

    Result<Boolean> register(RegisterReq registerReq);

    Result<Boolean> resetPwd(ResetPwdReq resetPwdReq);

    Result<UserDO> getUser(Integer id);

    Result<Boolean> changePwd(ChangePwdReq changePwdReq);

    Result<Boolean> changeMsg(ChangeMsgReq changeMsgReq);

    Result<Boolean> getVerifyCode(String username, String email);
}
