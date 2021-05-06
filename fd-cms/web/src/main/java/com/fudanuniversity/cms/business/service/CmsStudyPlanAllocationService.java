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
     * 管理员生成培养计划
     */
    void generateUserAllocations(CmsStudyPlanAllocationGenerateVo generateVo);

    /**
     * 管理员更新培养计划状态
     */
    void editAllocation(CmsStudyPlanAllocationEditVo editVo);

    /**
     * 根据id删除处理
     */
    void deleteAllocationById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsStudyPlanAllocationVo> queryPagingResult(CmsStudyPlanAllocationQueryVo queryVo, Paging paging);

    /**
     * 用户更新培养计划状态
     */
    void editUserAllocation(Long userId, CmsStudyPlanAllocationUserEditVo userEditVo);

    /**
     * 用户查询自己的培养计划信息
     */
    CmsStudyPlanAllocationInfoVo queryUserAllocationInfo(Long userId, Long planId);
}