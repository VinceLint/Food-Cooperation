package cn.scau.springcloud.client;

import cn.scau.springcloud.domain.Product;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.vo.UserVO;
import cn.scau.springcloud.form.UserForm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserClientFeignHystrix implements UserClientFeign {

    @Override
    public Result<UserVO> login(UserForm userform) {
        return Result.sysErrResult("login system paralyzed");
    }
}
