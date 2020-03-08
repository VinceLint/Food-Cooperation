package cn.scau.springcloud.domain.request;

import lombok.Data;

@Data
public class ApplyPassReq {
    private Integer cooperationId;
    private Integer cooperatorId;
}
