package com.fudanuniversity.cms.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 培养方案分配
 * <p>
 * Created by tidu at 2021-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsStudyPlanAllocation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:学生id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long userId;

    /**
     * 字段备注:培养方案id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long planId;

    /**
     * 字段备注:培养方案开始时间 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Date planStartTime;

    /**
     * 字段备注:培养方案到期时间 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Date planEndTime;

    /**
     * 字段备注:计划天数 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Integer spendDays;

    /**
     * 字段备注:延期天数 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Integer delayDays;

    /**
     * 字段备注:备注 <p>
     * 数据库字段长度:(16383,0) <p>
     * 是否索引:不是
     */
    private String remark;

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

