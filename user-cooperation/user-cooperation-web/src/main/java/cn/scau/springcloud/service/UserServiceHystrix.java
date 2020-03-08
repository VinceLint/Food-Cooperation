package cn.scau.springcloud.service;

import cn.scau.springcloud.domain.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserServiceHystrix implements UserService {
    @Override
    public UserDTO getUserById(Integer id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("/");
        return userDTO;
    }
}
