package cn.scau.springcloud.dao;

import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.UserDO;

public interface UserDao {
    Result<UserDO> getUserByUsername(String username);

    Result<UserDO> insert(UserDO userDO);

    Result<UserDO> update(UserDO userDO);
}
