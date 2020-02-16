package cn.scau.springcloud.client;

import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.vo.UserVO;
import cn.scau.springcloud.form.UserForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 林跃涛
 * @version 1.0
 * @date 2019/9/17 19:58
 */
@FeignClient(value = "SESSION-SERVICE",fallback = UserClientFeignHystrix.class)
public interface UserClientFeign {
    @PostMapping("/user/login")
    public Result<UserVO> login(UserForm userform);
}
