package cn.scau.springcloud.mapper;

import cn.scau.springcloud.domain.entity.CooperationApplyDO;
import cn.scau.springcloud.domain.query.CooperationApplyQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CooperationApplyMapper extends BaseMapper<CooperationApplyDO, CooperationApplyQuery> {

    List<Integer> getScoreListByUserId(@Param("userId") Integer userId);
}
