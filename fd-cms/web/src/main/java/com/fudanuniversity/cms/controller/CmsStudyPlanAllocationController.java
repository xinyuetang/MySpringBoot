package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsStudyPlanAllocationService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationUpdateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationVo;
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
 * CmsStudyPlanAllocationController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 18:08:08
 */
@RestController
@RequestMapping("/study/plan/allocation")
public class CmsStudyPlanAllocationController extends BaseController {

    @Resource
    private CmsStudyPlanAllocationService cmsStudyPlanAllocationService;

    /**
     * 保存处理
     */
    @PostMapping("/save")
    public JsonResult<?> saveCmsStudyPlanAllocation(@Valid CmsStudyPlanAllocationAddVo addVo) {
        cmsStudyPlanAllocationService.saveCmsStudyPlanAllocation(addVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id更新处理
     */
    @PostMapping("/update")
    public JsonResult<?> updateCmsStudyPlanAllocationById(@Valid CmsStudyPlanAllocationUpdateVo updateVo) {
        cmsStudyPlanAllocationService.updateCmsStudyPlanAllocationById(updateVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id删除处理
     */
    @PostMapping("/delete")
    public JsonResult<?> deleteCmsStudyPlanAllocationById(@NotNull @Min(1L) Long id) {
        cmsStudyPlanAllocationService.deleteCmsStudyPlanAllocationById(id);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据条件查询信息列表
     */
    @GetMapping("/paging")
    public JsonResult<List<CmsStudyPlanAllocationVo>> queryPagingResult(@Valid CmsStudyPlanAllocationQueryVo queryVo, @Valid Paging paging) {
        PagingResult<CmsStudyPlanAllocationVo> pagingResult = cmsStudyPlanAllocationService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}