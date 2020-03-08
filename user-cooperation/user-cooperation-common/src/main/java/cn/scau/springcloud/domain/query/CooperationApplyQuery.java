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
public class CooperationApplyQuery extends Query implements Serializable {
    /** id*/
    private Integer id;

    /** 合作号*/
    private Integer cooperationId;

    private List<Integer> cooperationIds;

    /** 合作者id*/
    private Integer cooperatorId;

    /** 0:待审核 1:通过 2:拒绝*/
    private Integer status;

    private String comment;

    private Integer score;

    private Date createdAt;

    private Date updatedAt;

    /** 删除时间*/
    private Date deletedAt;

    private Integer isDeleted;
}
