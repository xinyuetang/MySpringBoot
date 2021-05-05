package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 培养方案分配
 *
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanAllocationQueryVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 培养方案id
     */
    private Long planId;

    /**
     * 培养方案阶段id
     */
    private Long planStageId;

    /**
     * 培养方案任务id
     */
    private Long planWorkId;

    /**
     * 培养方案任务预计节点日期
     */
    private Date planWorkExpectExpireTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标记
     */
    private Long deleted;

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