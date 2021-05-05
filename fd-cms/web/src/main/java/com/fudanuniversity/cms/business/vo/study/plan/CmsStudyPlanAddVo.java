package com.fudanuniversity.cms.business.vo.study.plan;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 培养方案
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 入学年份
     */
    @NotNull
    private Integer enrollYear;

    /**
     * 名称
     */
    @NotEmpty
    private String name;
}

