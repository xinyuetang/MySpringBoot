package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsStudyPlanWorkService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkUpdateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsStudyPlanWorkController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 18:08:08
 */
@RestController
@RequestMapping("/study/plan/work")
public class CmsStudyPlanWorkController extends BaseController {

    @Resource
    private CmsStudyPlanWorkService cmsStudyPlanWorkService;

    /**
     * 保存处理
     */
    @PostMapping("/save")
    public JsonResult<?> saveCmsStudyPlanWork(@Valid @RequestBody CmsStudyPlanWorkAddVo addVo) {
        cmsStudyPlanWorkService.saveCmsStudyPlanWork(addVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id更新处理
     */
    @PostMapping("/update")
    public JsonResult<?> updateCmsStudyPlanWorkById(@Valid @RequestBody CmsStudyPlanWorkUpdateVo updateVo) {
        cmsStudyPlanWorkService.updateCmsStudyPlanWorkById(updateVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id删除处理
     */
    @PostMapping("/delete")
    public JsonResult<?> deleteCmsStudyPlanWorkById(@NotNull @Min(1L) Long id) {
        cmsStudyPlanWorkService.deleteCmsStudyPlanWorkById(id);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据条件查询信息列表
     */
    @GetMapping("/list")
    public JsonResult<?> queryCmsStudyPlanWorkList(@Valid CmsStudyPlanWorkQueryVo queryVo) {
        List<CmsStudyPlanWorkVo> pagingResult = cmsStudyPlanWorkService.queryCmsStudyPlanWorkList(queryVo);
        return JsonResult.buildSuccess(pagingResult);
    }
}