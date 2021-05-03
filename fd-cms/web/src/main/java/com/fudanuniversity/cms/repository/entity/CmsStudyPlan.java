package com.fudanuniversity.cms.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 培养方案
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsStudyPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:是否公共任务 <p>
     * 数据库字段长度:(3,0) <p>
     * 是否索引:是
     */
    private Integer common;

    /**
     * 字段备注:是否科硕任务 <p>
     * 数据库字段长度:(3,0) <p>
     * 是否索引:不是
     */
    private Integer keshuo;

    /**
     * 字段备注:就读类型 <p>
     * 数据库字段长度:(3,0) <p>
     * 是否索引:不是
     */
    private Integer studyType;

    /**
     * 字段备注:序号 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Integer index;

    /**
     * 字段备注:名称 <p>
     * 数据库字段长度:(32,0) <p>
     * 是否索引:不是
     */
    private String name;

    /**
     * 字段备注:计划天数 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Integer spendDays;

    /**
     * 字段备注:创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Date createTime;

    /**
     * 字段备注:更新时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Date modifyTime;
}

