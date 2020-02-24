package cn.scau.springcloud.dao.impl;

import cn.scau.springcloud.dao.CooperationDao;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.mapper.CooperationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

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
        try{
            Integer result = cooperationMapper.insertSelective(cooperationDO);
            if (result!=null&&result>0){
                return Result.successResult(Boolean.TRUE);
            }
            log.error("insert cooperation fail, message:{}",cooperationDO.toString());
            return Result.argsErrResult();
        }catch (Exception e){
            log.error("insert cooperation fail, message:{}, exception:{}", cooperationDO.toString(), e.getMessage());
            return Result.sysErrResult();
        }
    }
}
