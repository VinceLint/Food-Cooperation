package cn.scau.springcloud.dao.impl;

import cn.scau.springcloud.dao.CooperationApplyDao;
import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationApplyDO;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.query.CooperationApplyQuery;
import cn.scau.springcloud.helper.DaoHelper;
import cn.scau.springcloud.mapper.CooperationApplyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
@Slf4j
public class CooperationApplyDaoImpl implements CooperationApplyDao {
    @Resource
    private CooperationApplyMapper cooperationApplyMapper;

    @Override
    public Result<CooperationApplyDO> queryOne(CooperationApplyQuery query) {
        if (query == null) {
            return Result.argsErrResult();
        }
        return DaoHelper.queryOne(cooperationApplyMapper, query);
    }

    @Override
    public Result<CooperationApplyDO> insert(CooperationApplyDO cooperationApplyDO) {
        if (cooperationApplyDO == null) {
            return Result.argsErrResult();
        }
        return DaoHelper.insert(cooperationApplyMapper, cooperationApplyDO);
    }

    @Override
    public PageResult<CooperationApplyDO> query(CooperationApplyQuery query) {
        if (query == null) {
            return PageResult.argErrResult();
        }
        return DaoHelper.query(cooperationApplyMapper, query);
    }

    @Override
    public Result<Boolean> update(CooperationApplyDO cooperationApplyDO) {
        if (cooperationApplyDO == null || cooperationApplyDO.getId() == null) {
            return Result.argsErrResult();
        }
        return DaoHelper.update(cooperationApplyMapper, cooperationApplyDO);
    }

    @Override
    public Result<List<Integer>> getScoreList(Integer userId) {
        if (userId == null){
            return Result.argsErrResult();
        }
        List<Integer> result = cooperationApplyMapper.getScoreListByUserId(userId);
        return Result.successResult(result);
    }
}
