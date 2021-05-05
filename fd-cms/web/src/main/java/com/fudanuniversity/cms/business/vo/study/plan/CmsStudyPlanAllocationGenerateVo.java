package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


/**
 * 培养方案分配
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanAllocationGenerateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 培养方案id
     */
    private Long planId;

    /**
     * 用户id
     */
    private List<Long> userIds;
}

