package com.fudanuniversity.cms.controller.user;

import com.fudanuniversity.cms.business.service.CmsStudyPlanAllocationService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationInfoVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationOverviewVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.controller.BaseController;
import io.swagger.annotations.Api;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * CmsStudyPlanAllocationController
 * <p>
 * Created by Xinyue.Tang at 2021-05-07 11:39:06
 */
@Api(tags = "用户 - 培养计划分配")
@RestController
@RequestMapping("/u/study/plan/allocation")
public class CmsStudyPlanAllocationController extends BaseController {

    @Resource
    private CmsStudyPlanAllocationService cmsStudyPlanAllocationService;

    /**
     * 管理员或用户查看培养计划任务完成情况
     */
    @GetMapping("/info")
    public JsonResult<?> queryAllocationInfo(
            @NotNull(message = "培养计划id不能为空") @Min(1L) Long id) {
        Long userId = getLoginUser().getUserId();
        CmsStudyPlanAllocationInfoVo allocationInfoVo = cmsStudyPlanAllocationService.queryAllocationInfo(id, userId);
        return JsonResult.buildSuccess(allocationInfoVo);
    }

    /**
     * 管理员或用户预览用户分配的培养计划
     */
    @GetMapping("/overview")
    public JsonResult<?> queryUserCmsStudyPlanOverview(
            @NotNull(message = "培养计划id不能为空") @Min(1L) Long id) {
        Long userId = getLoginUser().getUserId();
        CmsStudyPlanAllocationOverviewVo overview = cmsStudyPlanAllocationService.queryUserAllocationOverview(userId, id);
        return JsonResult.buildSuccess(overview);
    }
}