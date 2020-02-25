package cn.scau.springcloud.helper;

import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.vo.CooperationVO;
import cn.scau.springcloud.util.DateUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CooperationHelper {
    public static List<CooperationVO> transferCommonCooperationVOs(List<CooperationDO> cooperationDOList){
        return Optional.ofNullable(cooperationDOList).orElseGet(ArrayList::new)
                .stream().map(CooperationHelper::transferVO).collect(Collectors.toList());
    }

    private static CooperationVO transferVO(CooperationDO cooperationDO) {
        CooperationVO cooperationVO = new CooperationVO();
        BeanUtils.copyProperties(cooperationDO, cooperationVO);
        cooperationVO.setPublishTimeStr(DateUtils.date2Str(cooperationDO.getPublishTime()));
        return cooperationVO;
    }
}
