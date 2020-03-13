package cn.scau.springcloud.service;


import cn.scau.springcloud.domain.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "SESSION-SERVICE", fallback = UserServiceHystrix.class)
public interface UserService {
    @GetMapping("/user-api/getUser")
    UserDTO getUserById(@RequestParam("id") Integer id);

    @GetMapping("/user-api/syncScore")
    void syncScore(@RequestParam("id") Integer id, @RequestParam("score") Float score);
}
