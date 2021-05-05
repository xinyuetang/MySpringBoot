package com.fudanuniversity.cms.commons.enums;

/**
 * Created by Xinyue.Tang at 2021-05-06 02:18:34
 */
public enum StudyPlanAllocationStatusEnum {

    Underway(10, "进行中"),
    Finished(20, "已归还"),
    ;

    private final Integer code;

    private final String desc;

    StudyPlanAllocationStatusEnum(Integer code, String desc) {
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
