package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlan;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanQuery;

/**
 * CmsStudyPlanService
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
public interface CmsStudyPlanService {

    /**
     * 保存处理
     */
    void saveCmsStudyPlan(CmsStudyPlan cmsStudyPlan);

    /**
     * 根据id更新处理
     */
    void updateCmsStudyPlanById(CmsStudyPlan cmsStudyPlan);

    /**
     * 根据id删除处理
     */
    void deleteCmsStudyPlanById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsStudyPlan> queryPagingResult(CmsStudyPlanQuery query);

}