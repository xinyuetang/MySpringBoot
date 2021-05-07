package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationInfoVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationOverviewVo;

import java.util.List;

/**
 * CmsStudyPlanAllocationService
 * <p>
 * Created by Xinyue.Tang at 2021-05-07 11:39:06
 */
public interface CmsStudyPlanAllocationService {

    /**
     *
     */
    List<CmsStudyPlanAllocationInfoVo> queryAllocationInfoList(Long planId);


    /**
     *
     */
    void deleteCmsStudyPlanAllocationById(Long id, Long userId);

    /**
     *
     */
    CmsStudyPlanAllocationInfoVo queryAllocationInfo(Long planId, Long userId);

    /**
     *
     */
    CmsStudyPlanAllocationOverviewVo queryUserAllocationOverview(Long userId, Long planId);
}