package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanAllocation;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanAllocationQuery;

/**
 * CmsStudyPlanAllocationService
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
public interface CmsStudyPlanAllocationService {

    /**
     * 保存处理
     */
    void saveCmsStudyPlanAllocation(CmsStudyPlanAllocation cmsStudyPlanAllocation);

    /**
     * 根据id更新处理
     */
    void updateCmsStudyPlanAllocationById(CmsStudyPlanAllocation cmsStudyPlanAllocation);

    /**
     * 根据id删除处理
     */
    void deleteCmsStudyPlanAllocationById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsStudyPlanAllocation> queryPagingResult(CmsStudyPlanAllocationQuery query);

}