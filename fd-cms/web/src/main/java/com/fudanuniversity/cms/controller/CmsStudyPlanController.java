package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsStudyPlanService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanUpdateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsStudyPlanController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@RestController
@RequestMapping("/study/plan")
public class CmsStudyPlanController extends BaseController {

    @Resource
    private CmsStudyPlanService cmsStudyPlanService;

    /**
     * 保存处理
     */
    @PostMapping("/save")
    public JsonResult<?> saveCmsStudyPlan(@Valid CmsStudyPlanAddVo addVo) {
        cmsStudyPlanService.saveCmsStudyPlan(addVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id更新处理
     */
    @PostMapping("/update")
    public JsonResult<?> updateCmsStudyPlanById(@Valid CmsStudyPlanUpdateVo updateVo) {
        cmsStudyPlanService.updateCmsStudyPlanById(updateVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id删除处理
     */
    @PostMapping("/delete")
    public JsonResult<?> deleteCmsStudyPlanById(@NotNull @Min(1L) Long id) {
        cmsStudyPlanService.deleteCmsStudyPlanById(id);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据条件查询信息列表
     */
    @GetMapping("/paging")
    public JsonResult<List<CmsStudyPlanVo>> queryPagingResult(@Valid CmsStudyPlanQueryVo queryVo, Paging paging) {
        PagingResult<CmsStudyPlanVo> pagingResult = cmsStudyPlanService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}