package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanStageAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanStageQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanStageUpdateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanStageVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

/**
 * CmsStudyPlanStageService
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:54:25
 */
public interface CmsStudyPlanStageService {

    /**
     * 保存处理
     * @param addVo
     */
    void saveCmsStudyPlanStage(CmsStudyPlanStageAddVo addVo);

    /**
     * 根据id更新处理
     * @param updateVo
     */
    void updateCmsStudyPlanStageById(CmsStudyPlanStageUpdateVo updateVo);

    /**
     * 根据id删除处理
     */
    void deleteCmsStudyPlanStageById(Long id);

    /**
     * 分页查询数据列表
     * @return
     */
    PagingResult<CmsStudyPlanStageVo> queryPagingResult(CmsStudyPlanStageQueryVo queryVo, Paging paging);

}