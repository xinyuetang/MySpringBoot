package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 培养方案
 *
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsStudyPlanQueryVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 是否公共任务
     */
    private Integer common;

    /**
     * 是否科硕任务
     */
    private Integer keshuo;

    /**
     * 就读类型
     */
    private Integer studyType;

    /**
     * 序号
     */
    private Integer index;

    /**
     * 名称
     */
    private String name;

    /**
     * 计划天数
     */
    private Integer spendDays;

    /**
     * 小于等于创建时间
     */
    private Date eltCreateTime;

    /**
     * 大于等于创建时间
     */
    private Date egtCreateTime;

    /**
     * 小于等于更新时间
     */
    private Date eltModifyTime;

    /**
     * 大于等于更新时间
     */
    private Date egtModifyTime;
}