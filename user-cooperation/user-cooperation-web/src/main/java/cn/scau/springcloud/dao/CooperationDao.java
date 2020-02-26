package cn.scau.springcloud.dao;

import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.query.CooperationQuery;

public interface CooperationDao {
    public Result<Boolean> insert(CooperationDO cooperationDO);

    PageResult<CooperationDO> listCommonMsg(CooperationQuery query);

    Result<CooperationDO> queryOne(CooperationQuery query);
}
