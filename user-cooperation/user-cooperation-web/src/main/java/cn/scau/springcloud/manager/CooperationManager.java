package cn.scau.springcloud.manager;

import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.request.CooperationReq;
import cn.scau.springcloud.domain.vo.CooperationVO;

public interface CooperationManager {
    Result<Boolean> publish(CooperationReq cooperationReq);

    PageResult<CooperationDO> listCommonMsg(Integer page, Integer pageSize);

    Result<CooperationDO> commonMsg(Integer id);

    Result<Boolean> apply(Integer id);

    PageResult<CooperationDO> purchaserCooperation(Integer page, Integer pageSize, Integer status);

    PageResult<CooperationDO> bossCooperation(Integer page, Integer pageSize, Integer status);
}
