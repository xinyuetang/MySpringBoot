package com.fudanuniversity.cms.inner.entity.deprecated;

public class Class {
    private Integer id;
    private Integer tag;//0=推荐论文管理; 1=培养方案管理；
    private String className;//类名


    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
