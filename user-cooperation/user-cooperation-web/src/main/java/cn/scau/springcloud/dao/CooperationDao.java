package cn.scau.springcloud.dao;

import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationDO;

public interface CooperationDao {
    public Result<Boolean> insert(CooperationDO cooperationDO);
}
