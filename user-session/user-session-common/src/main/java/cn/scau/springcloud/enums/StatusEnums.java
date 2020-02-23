package cn.scau.springcloud.enums;

import lombok.Getter;

public enum StatusEnums {
    NORMAL(0, "正常"),
    FROZEN(1, "冻结"),
    DELETE(2, "删除");
    @Getter
    private Integer type;
    @Getter
    private String desc;

    StatusEnums(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
