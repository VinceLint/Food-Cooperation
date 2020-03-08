package cn.scau.springcloud.manager;

import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationApplyDO;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.request.CooperationReq;
import cn.scau.springcloud.domain.vo.CooperationApplyVO;
import cn.scau.springcloud.domain.vo.CooperationVO;

import java.util.Map;

public interface CooperationManager {
    Result<Boolean> publish(CooperationReq cooperationReq);

    PageResult<CooperationDO> listCommonMsg(Integer page, Integer pageSize);

    Result<CooperationDO> commonMsg(Integer id);

    Result<Boolean> apply(Integer id);

    PageResult<CooperationDO> purchaserCooperation(Integer page, Integer pageSize, Integer status);

    PageResult<CooperationApplyVO> bossCooperation(Integer page, Integer pageSize, Integer status, Integer applyStatus);

    Result<Boolean> pass(Integer cooperationId, Integer cooperatorId);

    Result<Boolean> reject(Integer cooperationId, Integer cooperatorId, String comment);

    Result<Boolean> end(Integer cooperationId, Integer cooperatorId, Integer status, String comment, Integer score);
}
