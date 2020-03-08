package cn.scau.springcloud.domain.vo;

import cn.scau.springcloud.domain.entity.UserDO;
import lombok.Data;
import org.apache.catalina.User;

@Data
public class CooperationApplyVO {

    /** 合作号*/
    private Integer cooperationId;

    private CooperationVO cooperationVO;

    /** 合作者id*/
    private Integer cooperatorId;

    /** 合作者*/
    private UserVO user;

    /** 0:待审核 1:通过 2:拒绝*/
    private Integer status;

    private String comment;

    private Integer score;


}
