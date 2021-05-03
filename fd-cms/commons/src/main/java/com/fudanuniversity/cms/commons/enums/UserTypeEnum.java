package com.fudanuniversity.cms.commons.enums;

/**
 * Created by tidu at 2021-05-03 22:12:54
 */
public enum UserTypeEnum {

    Teacher(10, "老师"),
    Student(20, "学生"),
    ;

    private final Integer code;

    private final String desc;

    UserTypeEnum(Integer code, String desc) {
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
