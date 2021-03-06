package cn.scau.springcloud.dao.impl;

import cn.scau.springcloud.dao.UserDao;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.UserDO;
import cn.scau.springcloud.domain.query.UserQuery;
import cn.scau.springcloud.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Repository
@Slf4j
public class UserDaoImpl implements UserDao {
    @Resource
    private UserMapper userMapper;

    @Override
    public Result<UserDO> getUserByUsername(String username) {
        if (username == null) {
            return Result.argsErrResult("username is null");
        }
        UserQuery userQuery = new UserQuery();
        userQuery.setUsername(username);
        UserDO userDO = userMapper.queryOne(userQuery);
        if (userDO == null) {
            return Result.argsErrResult("username is not exist");
        }
        return Result.successResult(userDO);
    }

    @Override
    public Result<UserDO> insert(UserDO userDO) {
        if (userDO == null) {
            return Result.argsErrResult("user is null");
        }
        Integer result = userMapper.insertSelective(userDO);
        if (result == null || result <= 0) {
            return Result.sysErrResult("insert user fail");
        }
        return Result.successResult(userDO);
    }

    @Override
    @Transactional
    public Result<UserDO> update(UserDO userDO) {
        if (userDO == null) {
            return Result.argsErrResult("user is null");
        }
        UserQuery userQuery = new UserQuery();
        userQuery.setUsername(userDO.getUsername());
        UserDO oldUserDO = userMapper.queryOne(userQuery);
        if (oldUserDO == null) {
            return Result.argsErrResult("user is not exist");
        }
        Integer result = userMapper.updateByPrimaryKeySelective(userDO);
        if (result == null || result <= 0) {
            return Result.sysErrResult();
        }
        return Result.successResult(userDO);
    }

    @Override
    public Result<UserDO> getUserById(Integer id) {
        if (id == null || id <= 0) {
            System.out.println("gub"+id);
            return Result.argsErrResult();
        }
        UserQuery userQuery = new UserQuery();
        userQuery.setId(id);
        UserDO userDO = userMapper.queryOne(userQuery);
        if (userDO == null) {
            return Result.argsErrResult("id is not exist");
        }
        return Result.successResult(userDO);
    }
}
