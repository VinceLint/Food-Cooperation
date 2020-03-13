package cn.scau.springcloud.mapper;

import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.query.CooperationQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CooperationMapper extends BaseMapper<CooperationDO, CooperationQuery> {
    List<Integer> getScoreListByUserId(@Param("userId") Integer userId);
}
