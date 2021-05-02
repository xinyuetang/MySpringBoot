package com.fudanuniversity.cms.commons.enums;

/**
 * Created by tidu at 2021-05-02 16:53:32
 */
public enum DeviceAllocationStatusEnum {

    InUse(0, "使用中"),
    Returned(1, "已归还"),
    ;

    private final Integer code;

    private final String desc;

    DeviceAllocationStatusEnum(Integer code, String desc) {
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
