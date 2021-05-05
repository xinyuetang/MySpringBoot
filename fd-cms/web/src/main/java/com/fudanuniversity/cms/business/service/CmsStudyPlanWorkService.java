package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkUpdateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

/**
 * CmsStudyPlanWorkService
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:54:25
 */
public interface CmsStudyPlanWorkService {

    /**
     * 保存处理
     * @param addVo
     */
    void saveCmsStudyPlanWork(CmsStudyPlanWorkAddVo addVo);

    /**
     * 根据id更新处理
     * @param updateVo
     */
    void updateCmsStudyPlanWorkById(CmsStudyPlanWorkUpdateVo updateVo);

    /**
     * 根据id删除处理
     */
    void deleteCmsStudyPlanWorkById(Long id);

    /**
     * 分页查询数据列表
     * @return
     */
    PagingResult<CmsStudyPlanWorkVo> queryPagingResult(CmsStudyPlanWorkQueryVo queryVo, Paging paging);

}