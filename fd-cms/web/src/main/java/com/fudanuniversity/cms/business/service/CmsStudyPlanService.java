package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanUpdateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

/**
 * CmsStudyPlanService
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:54:25
 */
public interface CmsStudyPlanService {

    /**
     * 保存处理
     */
    void saveCmsStudyPlan(CmsStudyPlanAddVo cmsStudyPlan);

    /**
     * 根据id更新处理
     */
    void updateCmsStudyPlanById(CmsStudyPlanUpdateVo cmsStudyPlan);

    /**
     * 根据id删除处理
     */
    void deleteCmsStudyPlanById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsStudyPlanVo> queryPagingResult(CmsStudyPlanQueryVo query, Paging paging);

}