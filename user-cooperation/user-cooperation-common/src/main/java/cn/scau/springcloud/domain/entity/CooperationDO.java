package cn.scau.springcloud.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CooperationDO extends BaseDO {


    /** 合作标题*/
    private String title;

    /** 餐厅名*/
    private String company;

    /** 省份*/
    private String province;

    /** 城市*/
    private String city;

    /** 地址*/
    private String address;

    /** 发布时间*/
    private Date publishTime;

    /** 具体合作内容*/
    private String detail;

    /** 0:未合作 1:已找到合作 2:已结束 3提前结束*/
    private Integer status;

    /** 发布者id*/
    private Integer userId;

    /** 采购人员id*/
    private Integer purchaserId;

    private String comment;

    private Integer score;

    /** 删除时间*/
    private Date deletedAt;


}

