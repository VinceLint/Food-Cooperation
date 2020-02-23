package cn.scau.springcloud.manager;

import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.dto.UserDTO;
import cn.scau.springcloud.domain.request.LoginReq;
import cn.scau.springcloud.domain.request.RegisterReq;
import cn.scau.springcloud.domain.request.ResetPwdReq;
import cn.scau.springcloud.domain.response.LoginResp;
import cn.scau.springcloud.domain.response.RegisterResp;

public interface UserManager {
    Result<LoginResp> login(LoginReq loginReq);

    Result<Boolean> register(RegisterReq registerReq);

    Result<Boolean> resetPwd(ResetPwdReq resetPwdReq);
}
