package cn.scau.springcloud.domain.vo;

import cn.scau.springcloud.domain.entity.BaseDO;
import lombok.Data;
import org.apache.catalina.User;

import java.util.Date;

@Data
public class CooperationVO {

    /** id*/
    private Integer id;

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

    /** 发布时间string*/
    private String publishTimeStr;

    /** 具体合作内容*/
    private String detail;

    /** 发布者id*/
    private UserVO user;

}
