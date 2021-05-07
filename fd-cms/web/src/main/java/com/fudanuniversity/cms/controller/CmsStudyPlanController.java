package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsStudyPlanService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;

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

    @Resource
    private CmsUserService cmsUserService;

    /**
     * 保存处理
     */
    @PostMapping("/add")
    public JsonResult<?> saveCmsStudyPlan(@Valid @RequestBody CmsStudyPlanAddVo addVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanService.saveCmsStudyPlan(addVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 创建一个完整的培养计划
     */
    @PostMapping("/create")
    public JsonResult<?> saveCmsStudyPlan(@Valid @RequestBody CmsStudyPlanFullVo fullVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanService.createFullCmsStudyPlan(fullVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id更新处理
     */
    @PostMapping("/update")
    public JsonResult<?> updateCmsStudyPlanById(@Valid @RequestBody CmsStudyPlanUpdateVo updateVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanService.updateCmsStudyPlanById(updateVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id删除处理
     */
    @PostMapping("/delete")
    public JsonResult<?> deleteCmsStudyPlanById(@NotNull @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanService.deleteCmsStudyPlanById(id);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据条件查询信息列表
     */
    @GetMapping("/paging")
    public JsonResult<?> queryPagingResult(@Valid CmsStudyPlanQueryVo queryVo, Paging paging) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        PagingResult<CmsStudyPlanVo> pagingResult = cmsStudyPlanService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    /**
     * 管理员预览培养计划
     */
    @GetMapping("/overview")
    public JsonResult<?> queryCmsStudyPlanOverview(@NotNull(message = "培养计划id不能为空") @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        CmsStudyPlanOverviewVo overview = cmsStudyPlanService.queryCmsStudyPlanOverview(id);
        return JsonResult.buildSuccess(overview);
    }

    /**
     * 管理员预览培养计划分配给用户
     */
    @PostMapping("/assign")
    public JsonResult<?> assignCmsStudyPlan(@Valid @RequestBody CmsStudyPlanAssignVo assignVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanService.assignCmsStudyPlan(assignVo);
        return JsonResult.buildSuccess();
    }
}