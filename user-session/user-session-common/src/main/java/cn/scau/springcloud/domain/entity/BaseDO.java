package cn.scau.springcloud.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDO {
    private Integer id;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Integer isDeleted;
}
