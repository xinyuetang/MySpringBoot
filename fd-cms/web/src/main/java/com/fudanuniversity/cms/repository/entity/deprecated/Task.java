package com.fudanuniversity.cms.repository.entity.deprecated;

public class Task {
    private Integer id;
    private String name;//任务名
    private Integer time;//任务完成时间限制（以天为单位）
    private Boolean isPublic;//是否公共任务
    private Boolean isKeShuo;//是否科硕必选任务
    private Integer type;//任务类型 0-学术型，1-结合型，2-技术型
}
