package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 培养方案阶段
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanStageUpdateVo implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * id
     */
	private Long id;

    /**
     * 培养方案id
     */
	private Long planId;

    /**
     * 学期
     */
	private Integer term;

    /**
     * 节点日期
     */
	private Date expireDate;

    /**
     * 创建时间
     */
	private Date createTime;

    /**
     * 更新时间
     */
	private Date modifyTime;
}

