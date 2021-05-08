package com.fudanuniversity.cms.controller.user;

import com.fudanuniversity.cms.business.service.CmsStudyPlanService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.controller.BaseController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * CmsStudyPlanController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@RestController
@RequestMapping("/u/study/plan")
public class CmsStudyPlanController extends BaseController {

    @Resource
    private CmsStudyPlanService cmsStudyPlanService;

    /**
     * 根据条件查询信息列表
     */
    @GetMapping("/paging")
    public JsonResult<?> queryPagingResult(@Valid CmsStudyPlanQueryVo queryVo, Paging paging) {
        PagingResult<CmsStudyPlanVo> pagingResult = cmsStudyPlanService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}