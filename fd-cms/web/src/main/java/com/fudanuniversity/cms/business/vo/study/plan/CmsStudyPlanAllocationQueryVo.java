package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 培养方案分配
 *
 * Created by tidu at 2021-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsStudyPlanAllocationQueryVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 学生id
     */
    private Long userId;

    /**
     * 培养方案id
     */
    private Long planId;

    /**
     * 培养方案开始时间
     */
    private Date planStartTime;

    /**
     * 培养方案到期时间
     */
    private Date planEndTime;

    /**
     * 计划天数
     */
    private Integer spendDays;

    /**
     * 延期天数
     */
    private Integer delayDays;

    /**
     * 备注
     */
    private String remark;

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