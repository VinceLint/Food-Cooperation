package cn.scau.springcloud.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDTO {
    private Integer id;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Integer isDeleted;
}
