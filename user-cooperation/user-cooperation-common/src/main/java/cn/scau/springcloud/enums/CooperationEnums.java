package cn.scau.springcloud.enums;

import lombok.Getter;

public enum CooperationEnums {
    SEEK(0, "未合作"),
    ON(1, "已合作"),
    NORMALFIN(2, "正常结束"),
    ABNORMALFIN(3, "非正常结束");
    @Getter
    private Integer type;
    @Getter
    private String desc;

    CooperationEnums(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
