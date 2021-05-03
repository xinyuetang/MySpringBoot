package com.fudanuniversity.cms.business.vo.study.plan;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 培养方案
 * <p>
 * Created by tidu at 2021-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsStudyPlanVo implements Serializable {

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
     * 创建时间
     */
	private Date createTime;

    /**
     * 更新时间
     */
	private Date modifyTime;
}

