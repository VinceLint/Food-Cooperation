package cn.scau.springcloud.domain.entity;

import lombok.Data;

@Data
public class CooperationApplyDO extends BaseDO {


    /** 合作号*/
    private Integer cooperationId;

    /** 合作者id*/
    private Integer cooperatorId;

    /** 0:待审核 1:通过 2:拒绝*/
    private Integer status;

}

