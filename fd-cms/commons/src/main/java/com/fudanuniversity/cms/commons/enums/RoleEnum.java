package com.fudanuniversity.cms.commons.enums;

/**
 * Created by tidu at 2021-05-01 18:11:32
 */
public enum RoleEnum {

    Administrator(0, "系统管理员"),
    Seminar(1, "讨论班管理员"),
    Lab(2, "Lab管理员"),
    EssayRecommendation(3, "推荐论文管理员"),
    TrainingScheme(4, "培养方案管理员"),
    Bulletin(5, "通知论文管理员"),
    General(6, "普通用户"),
    ;

    private final Integer code;

    private final String desc;

    RoleEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
