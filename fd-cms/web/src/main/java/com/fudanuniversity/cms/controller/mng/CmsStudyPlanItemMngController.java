package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsStudyPlanItemService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanItemEditVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;

/**
 * CmsStudyPlanAllocationController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 18:08:08
 */
@Api(tags = "管理员 - 用户分配的培养计划任务")
@RestController
@RequestMapping("/mng/study/plan/allocation")
public class CmsStudyPlanItemMngController extends BaseController {

    @Resource
    private CmsStudyPlanItemService cmsStudyPlanItemService;

    @Resource
    private CmsUserService cmsUserService;

    @Operation(summary = "管理员变更用户分配的培养计划任务状态")
    @PostMapping("/edit")
    public JsonResult<?> editAllocation(
            @Valid @RequestBody CmsStudyPlanItemEditVo editVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanItemService.editStudyPlanItem(editVo);
        return JsonResult.buildSuccess();
    }
}