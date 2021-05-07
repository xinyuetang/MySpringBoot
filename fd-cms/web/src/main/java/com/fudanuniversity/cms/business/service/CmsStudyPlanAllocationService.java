package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanItemGenerateVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

/**
 * CmsStudyPlanAllocationService
 * <p>
 * Created by Xinyue.Tang at 2021-05-07 11:39:06
 */
public interface CmsStudyPlanAllocationService {

    /**
     * 管理员生成培养计划
     */
    void generateUserAllocations(CmsStudyPlanItemGenerateVo generateVo);

    /**
     * 根据id删除处理
     */
    void deleteCmsStudyPlanAllocationById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsStudyPlanAllocationVo> queryPagingResult(CmsStudyPlanAllocationQueryVo queryVo, Paging paging);

}