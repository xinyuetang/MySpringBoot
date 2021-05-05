package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

/**
 * CmsStudyPlanAllocationService
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:54:25
 */
public interface CmsStudyPlanAllocationService {

    /**
     * 保存处理
     */
    void generateCmsStudyPlanAllocation(CmsStudyPlanAllocationGenerateVo generateVo);

    /**
     * 根据id更新处理
     */
    void updateCmsStudyPlanAllocationById(CmsStudyPlanAllocationUpdateVo updateVo);

    /**
     * 根据id删除处理
     */
    void deleteCmsStudyPlanAllocationById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsStudyPlanAllocationVo> queryPagingResult(CmsStudyPlanAllocationQueryVo queryVo, Paging paging);

    /**
     * 用户查询自己的培养计划
     */
    CmsStudyPlanAllocationInfoVo queryUserCmsStudyPlanAllocation(Long userId);
}