package cn.scau.springcloud.dao;

import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.query.CooperationQuery;

import java.util.List;

public interface CooperationDao {
    Result<Boolean> insert(CooperationDO cooperationDO);

    PageResult<CooperationDO> query(CooperationQuery query);

    Result<CooperationDO> queryOne(CooperationQuery query);

    PageResult<CooperationDO> queryIds(Integer userId, Integer status);

    Result<Boolean> update(CooperationDO cooperationDO);

    Result<List<Integer>> getScoreList(Integer userId);
}
