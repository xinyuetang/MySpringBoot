package com.fudanuniversity.cms.commons.enums;

/**
 * Created by Xinyue.Tang at 2021-05-01 18:11:32
 */
public enum UserRoleEnum {

    General(0, "普通用户"),
    Administrator(10, "系统管理员"),
    Seminar(20, "讨论班管理员"),
    Lab(30, "Lab管理员"),
    EssayRecommendation(40, "推荐论文管理员"),
    TrainingScheme(50, "培养方案管理员"),
    Bulletin(60, "通知论文管理员"),
    ;

    private final Integer code;

    private final String desc;

    UserRoleEnum(Integer code, String desc) {
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
