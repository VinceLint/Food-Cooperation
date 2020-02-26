package cn.scau.springcloud.enums;

import lombok.Getter;

public enum CooperationApplyEnums {
    APPLY(0, "待审核"),
    PASS(1, "通过"),
    REFUSE(2, "拒绝");
    @Getter
    private Integer type;
    @Getter
    private String desc;

    CooperationApplyEnums(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
