package cn.scau.springcloud.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CooperationQuery extends Query implements Serializable {
    /** id*/
    private Integer id;

    /** ids*/
    private List<Integer> ids;

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

    private Date createdAt;

    private Date updatedAt;

    /** 删除时间*/
    private Date deletedAt;

    private Integer isDeleted;
}
