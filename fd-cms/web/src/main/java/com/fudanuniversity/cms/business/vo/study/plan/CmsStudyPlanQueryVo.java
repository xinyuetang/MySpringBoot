package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 培养方案
 *
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanQueryVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 入学年份
     */
    private Integer enrollYear;

    /**
     * 名称
     */
    private String name;

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