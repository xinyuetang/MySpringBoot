package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsStudyPlanStageService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsStudyPlanStageController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 18:08:08
 */
@RestController
@RequestMapping("/study/plan/stage")
public class CmsStudyPlanStageController extends BaseController {

    @Resource
    private CmsStudyPlanStageService cmsStudyPlanStageService;

    /**
     * 保存处理
     */
    @PostMapping("/save")
    public JsonResult<?> saveCmsStudyPlanStage(@Valid @RequestBody CmsStudyPlanStageAddVo addVo) {
        cmsStudyPlanStageService.saveCmsStudyPlanStage(addVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id更新处理
     */
    @PostMapping("/update")
    public JsonResult<?> updateCmsStudyPlanStageById(@Valid @RequestBody CmsStudyPlanStageUpdateVo updateVo) {
        cmsStudyPlanStageService.updateCmsStudyPlanStageById(updateVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 批量更新处理
     */
    @PostMapping("/edit")
    public JsonResult<?> updateCmsStudyPlanStageById(@Valid @RequestBody List<CmsStudyPlanStageEditVo> editVoList) {
        cmsStudyPlanStageService.editCmsStudyPlanStages(editVoList);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id删除处理
     */
    @PostMapping("/delete")
    public JsonResult<?> deleteCmsStudyPlanStageById(@NotNull @Min(1L) Long id) {
        cmsStudyPlanStageService.deleteCmsStudyPlanStageById(id);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据条件查询信息列表
     */
    @GetMapping("/paging")
    public JsonResult<List<CmsStudyPlanStageVo>> queryPagingResult(@Valid CmsStudyPlanStageQueryVo queryVo, @Valid Paging paging) {
        PagingResult<CmsStudyPlanStageVo> pagingResult = cmsStudyPlanStageService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}