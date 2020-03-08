package cn.scau.springcloud.dao.impl;

import cn.scau.springcloud.dao.CooperationDao;
import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.query.CooperationQuery;
import cn.scau.springcloud.enums.CooperationEnums;
import cn.scau.springcloud.helper.DaoHelper;
import cn.scau.springcloud.mapper.CooperationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
@Slf4j
public class CooperationDaoImpl implements CooperationDao {

    @Resource
    private CooperationMapper cooperationMapper;

    @Override
    public Result<Boolean> insert(CooperationDO cooperationDO) {
        if (cooperationDO == null) {
            return Result.argsErrResult();
        }
        try {
            Integer result = cooperationMapper.insertSelective(cooperationDO);
            if (result != null && result > 0) {
                return Result.successResult(Boolean.TRUE);
            }
            log.error("insert cooperation fail, message:{}", cooperationDO.toString());
            return Result.argsErrResult();
        } catch (Exception e) {
            log.error("insert cooperation fail, message:{}, exception:{}", cooperationDO.toString(), e.getMessage());
            return Result.sysErrResult();
        }
    }

    @Override
    public PageResult<CooperationDO> query(CooperationQuery query) {
        return DaoHelper.query(cooperationMapper, query);
    }

    @Override
    public Result<CooperationDO> queryOne(CooperationQuery query) {
        return DaoHelper.queryOne(cooperationMapper, query);
    }

    @Override
    public PageResult<CooperationDO> queryIds(Integer userId, Integer status) {
        if (userId == null) {
            return PageResult.argErrResult();
        }
        CooperationQuery query = new CooperationQuery();
        query.setUserId(userId);
        query.setStatus(status);
        try {
            List<CooperationDO> cooperationDOList = cooperationMapper.query(query);
            return PageResult.successResult(cooperationDOList.size(), cooperationDOList);
        } catch (Exception e) {
            log.error("query cooperation fail, exception:{}", e.getMessage());
            return PageResult.exceptionResult(e);
        }
    }

    @Override
    public Result<Boolean> update(CooperationDO cooperationDO) {
        if (cooperationDO == null || cooperationDO.getId() == null) {
            return Result.argsErrResult();
        }
        Integer result = cooperationMapper.updateByPrimaryKeySelective(cooperationDO);
        if (result == null || result <= 0) {
            return Result.sysErrResult();
        }
        return Result.successResult(Boolean.TRUE);
    }
}
