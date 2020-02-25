package cn.scau.springcloud.manager;

import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.request.CooperationReq;

public interface CooperationManager {
    Result<Boolean> publish(CooperationReq cooperationReq);

    PageResult<CooperationDO> listCommonMsg(Integer page, Integer pageSize);
}
