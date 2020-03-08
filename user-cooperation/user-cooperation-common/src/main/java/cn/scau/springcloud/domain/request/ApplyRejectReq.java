package cn.scau.springcloud.domain.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class ApplyRejectReq {
    private Integer cooperationId;
    private Integer cooperatorId;

    @NotEmpty(message = "理由不能为空")
    @Length(min = 6, max = 255, message = "理由必须在{min}到{max}之间")
    private String comment;
}
