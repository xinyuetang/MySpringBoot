package com.fudanuniversity.cms.commons.enums;

/**
 * Created by Xinyue.Tang at 2021-05-05 18:01:11
 */
public enum StudyPlanWorkTypeEnum {

    Common(100, "公共"),

    Keshuo(200, "科硕"),

    Academic(310, "学术型"),

    Synthesizing(320, "结合型"),

    Technology(330, "技术型"),
    ;

    private final Integer code;

    private final String desc;

    StudyPlanWorkTypeEnum(Integer code, String desc) {
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
