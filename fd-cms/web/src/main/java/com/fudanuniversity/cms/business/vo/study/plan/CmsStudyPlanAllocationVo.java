package com.fudanuniversity.cms.business.vo.study.plan;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 培养方案分配
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsStudyPlanAllocationVo implements Serializable {

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
     * 创建时间
     */
	private Date createTime;

    /**
     * 更新时间
     */
	private Date modifyTime;
}

