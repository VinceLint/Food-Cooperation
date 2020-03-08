package cn.scau.springcloud.domain.request;

import cn.scau.springcloud.enums.CooperationEnums;
import lombok.Data;

import javax.validation.constraints.AssertTrue;

@Data
public class ApplyEndReq {
    private Integer cooperationId;
    private Integer cooperatorId;
    /**
     * 结束类型 2：正常结束，带score，comment   3：提前结束，不带score，comment
     */
    private Integer status;
    private Integer score;
    private String comment;

    @AssertTrue(message = "正常结束必须填写评论和分数")
    public boolean isCorrectWrite() {
        if (status == CooperationEnums.NORMALFIN.getType()) {
            if (comment == null || "".equals(comment) || score == null) {
                return false;
            }
        }
        return true;
    }


    @AssertTrue(message = "评论长度必须在6在255个字符之间")
    public boolean isCorrectLength() {
        if (status == CooperationEnums.NORMALFIN.getType()) {
            if (comment == null || comment.length() < 6 || comment.length() > 255) {
                return false;
            }
        }
        return true;
    }
}
