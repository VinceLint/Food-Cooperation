package cn.scau.springcloud.manager.impl;

import cn.scau.springcloud.context.UserSessionContextHolder;
import cn.scau.springcloud.dao.CooperationApplyDao;
import cn.scau.springcloud.dao.CooperationDao;
import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationApplyDO;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.query.CooperationApplyQuery;
import cn.scau.springcloud.domain.query.CooperationQuery;
import cn.scau.springcloud.domain.request.CooperationReq;
import cn.scau.springcloud.enums.CooperationApplyEnums;
import cn.scau.springcloud.enums.CooperationEnums;
import cn.scau.springcloud.manager.CooperationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CooperationManagerImpl implements CooperationManager {

    @Autowired
    private CooperationDao cooperationDao;

    @Autowired
    private CooperationApplyDao cooperationApplyDao;

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
        cooperationDO.setUserId(UserSessionContextHolder.getUserId());
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
        PageResult<CooperationDO> pageResult = cooperationDao.query(query);
        if (!pageResult.isSuccess()) {
            PageResult.errorResult(pageResult.getCode(), pageResult.getMsg());
        }
        return pageResult;
    }

    @Override
    public Result<CooperationDO> commonMsg(Integer id) {
        if (id == null || id <= 0) {
            return Result.argsErrResult();
        }
        CooperationQuery query = new CooperationQuery();
        query.setId(id);
        Result<CooperationDO> result = cooperationDao.queryOne(query);
        if (!result.isSuccess()) {
            return Result.errResult(result.getCode(), result.getMsg());
        }
        return Result.successResult(result.getResult());
    }

    @Override
    public Result<Boolean> apply(Integer id) {
        if (id == null || id <= 0) {
            return Result.argsErrResult("参数错误！");
        }
        // 检查该合作状态
        CooperationQuery cooperationQuery = new CooperationQuery();
        cooperationQuery.setId(id);
        Result<CooperationDO> cooperationDOResult = cooperationDao.queryOne(cooperationQuery);
        CooperationDO cooperationDO = cooperationDOResult.getResult();
        if (CooperationEnums.ON.getType() == cooperationDO.getStatus()) {
            return Result.errResult(cooperationDOResult.getCode(), "该合作已经被人抢先一步啦！");
        }
        if (CooperationEnums.NORMALFIN.getType() == cooperationDO.getStatus() ||
                CooperationEnums.ABNORMALFIN.getType() == cooperationDO.getStatus()) {
            return Result.errResult(cooperationDOResult.getCode(), "该合作已经结束啦！");
        }
        // 自己不能申请
        Integer userId = UserSessionContextHolder.getUserId();
        if (cooperationDOResult.getResult().getUserId() == userId) {
            return Result.errResult(cooperationDOResult.getCode(), "自己不能申请自己发布的消息哦");
        }
        // 检查是否重复申请
        CooperationApplyQuery cooperationApplyQuery = new CooperationApplyQuery();
        cooperationApplyQuery.setCooperationId(id);
        cooperationApplyQuery.setCooperatorId(UserSessionContextHolder.getUserId());
        Result<CooperationApplyDO> cooperationApplyDOResult = cooperationApplyDao.queryOne(cooperationApplyQuery);
        if (cooperationApplyDOResult.hasSuccessValue()) {
            return Result.errResult(cooperationApplyDOResult.getCode(), "您已经提交过了申请，请静候佳音");
        }
        // 提交申请
        CooperationApplyDO cooperationApplyDO = new CooperationApplyDO();
        cooperationApplyDO.setCooperationId(id);
        cooperationApplyDO.setCooperatorId(UserSessionContextHolder.getUserId());
        cooperationApplyDO.setStatus(CooperationApplyEnums.APPLY.getType());
        Result<CooperationApplyDO> result = cooperationApplyDao.insert(cooperationApplyDO);
        if (!result.isSuccess()) {
            return Result.errResult(result.getCode(), result.getMsg());
        }
        return Result.successResult(Boolean.TRUE);
    }

    @Override
    public PageResult<CooperationDO> purchaserCooperation(Integer page, Integer pageSize, Integer status) {
        // 先查询该用户的所有合作关系
        CooperationApplyQuery cooperationApplyQuery = new CooperationApplyQuery();
        cooperationApplyQuery.setCooperatorId(UserSessionContextHolder.getUserId());
        PageResult<CooperationApplyDO> applyDOPageResult = cooperationApplyDao.query(cooperationApplyQuery);
        if (!applyDOPageResult.isSuccess()) {
            return PageResult.errorResult(applyDOPageResult.getCode(), applyDOPageResult.getMsg());
        }
        List<CooperationApplyDO> cooperationApplyDOList = applyDOPageResult.getResults();
        if (cooperationApplyDOList.size() <= 0) {
            return PageResult.successResult(applyDOPageResult.getTotal(), new ArrayList<>());
        }

        // 根据用户合作关系得到ids去查询对应的合作细则
        List<Integer> ids = new ArrayList<>();
        cooperationApplyDOList.forEach(cooperationApplyDO -> {
            ids.add(cooperationApplyDO.getCooperationId());
        });
        CooperationQuery cooperationQuery = new CooperationQuery();
        cooperationQuery.setIds(ids);
        cooperationQuery.setStatus(status);
        cooperationQuery.setCurPage(page);
        cooperationQuery.setPageSize(pageSize);
        cooperationQuery.setWithCount(Boolean.TRUE);
        PageResult<CooperationDO> pageResult = cooperationDao.query(cooperationQuery);
        if (!pageResult.isSuccess()) {
            return PageResult.errorResult(pageResult.getCode(), pageResult.getMsg());
        }
        return pageResult;

    }

    @Override
    public PageResult<CooperationDO> bossCooperation(Integer page, Integer pageSize, Integer status) {
        CooperationQuery query = new CooperationQuery();
        query.setUserId(UserSessionContextHolder.getUserId());
        query.setStatus(status);
        query.setCurPage(page);
        query.setPageSize(pageSize);
        query.setWithCount(Boolean.TRUE);
        PageResult<CooperationDO> pageResult = cooperationDao.query(query);
        if (!pageResult.isSuccess()) {
            PageResult.errorResult(pageResult.getCode(), pageResult.getMsg());
        }
        return pageResult;
    }
}
