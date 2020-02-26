package cn.scau.springcloud.dao;

import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationApplyDO;
import cn.scau.springcloud.domain.query.CooperationApplyQuery;

public interface CooperationApplyDao {
    Result<CooperationApplyDO> queryOne(CooperationApplyQuery query);

    Result<CooperationApplyDO> insert(CooperationApplyDO cooperationApplyDO);
}
