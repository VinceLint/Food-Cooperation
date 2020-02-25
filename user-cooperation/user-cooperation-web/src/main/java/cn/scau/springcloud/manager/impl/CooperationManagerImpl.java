package cn.scau.springcloud.manager.impl;

import cn.scau.springcloud.dao.CooperationDao;
import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.query.CooperationQuery;
import cn.scau.springcloud.domain.request.CooperationReq;
import cn.scau.springcloud.enums.CooperationEnums;
import cn.scau.springcloud.manager.CooperationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class CooperationManagerImpl implements CooperationManager {

    @Autowired
    private CooperationDao cooperationDao;

    @Override
    public Result<Boolean> publish(CooperationReq cooperationReq) {
        CooperationDO cooperationDO = new CooperationDO();
        cooperationDO.setTitle(cooperationReq.getTitle());
        cooperationDO.setCompany(cooperationReq.getCompany());
        cooperationDO.setPublishTime(new Date());
        cooperationDO.setProvince(cooperationReq.getProvince());
        cooperationDO.setCity(cooperationReq.getCity());
        cooperationDO.setAddress(cooperationReq.getAddress());
        cooperationDO.setDetail(cooperationReq.getDetail());
        cooperationDO.setStatus(CooperationEnums.SEEK.getType());
        Result<Boolean> result = cooperationDao.insert(cooperationDO);
        if (!result.isSuccess()) {
            return Result.sysErrResult(result.getMsg());
        }
        return Result.successResult(Boolean.TRUE);
    }

    @Override
    public PageResult<CooperationDO> listCommonMsg(Integer page, Integer pageSize) {
        CooperationQuery query = new CooperationQuery();
        query.setStatus(CooperationEnums.SEEK.getType());
        query.setCurPage(page);
        query.setPageSize(pageSize);
        query.setWithCount(Boolean.TRUE);
        PageResult<CooperationDO> pageResult = cooperationDao.listCommonMsg(query);
        if (!pageResult.isSuccess()) {
            PageResult.errorResult(pageResult.getCode(), pageResult.getMsg());
        }
        return pageResult;
    }
}
