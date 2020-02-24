package cn.scau.springcloud.manager;

import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.request.CooperationReq;

public interface CooperationManager {
    public Result<Boolean> publish(CooperationReq cooperationReq);
}
