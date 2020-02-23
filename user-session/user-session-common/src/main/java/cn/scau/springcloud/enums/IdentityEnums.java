package cn.scau.springcloud.enums;

import lombok.Getter;

public enum IdentityEnums {
    NORMAL(0, "普通人员"),
    OPERATOR(1, "运营人员"),
    DEVELOPER(2, "开发人员");
    @Getter
    private Integer type;
    @Getter
    private String desc;

    IdentityEnums(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
