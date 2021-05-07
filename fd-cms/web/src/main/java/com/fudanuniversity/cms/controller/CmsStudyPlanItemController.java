package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsStudyPlanItemService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanItemEditVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanItemInfoVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanItemUserEditVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.commons.util.ValueUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;

/**
 * CmsStudyPlanAllocationController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 18:08:08
 */
@RestController
@RequestMapping("/study/plan/allocation")
public class CmsStudyPlanItemController extends BaseController {

    @Resource
    private CmsStudyPlanItemService cmsStudyPlanItemService;

    @Resource
    private CmsUserService cmsUserService;

    /**
     * 管理员变更培养计划分配状态
     */
    @PostMapping("/edit")
    public JsonResult<?> editAllocation(
            @Valid @RequestBody CmsStudyPlanItemEditVo editVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanItemService.editStudyPlanItem(editVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 用户点击完成任务
     */
    @PostMapping("/user/edit")
    public JsonResult<?> changeUserCmsStudyPlanAllocationStatus(
            @Valid @RequestBody CmsStudyPlanItemUserEditVo userEditVo) {
        LoginUser loginUser = getLoginUser();
        cmsStudyPlanItemService.editUserStudyPlanItem(loginUser.getUserId(), userEditVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 用户查询自己的培养计划
     */
    @GetMapping("/user/info")
    public JsonResult<?> queryUserCmsStudyPlanAllocation(Long userId, @NotNull Long planId) {
        LoginUser loginUser = getLoginUser();
        userId = ValueUtils.defaultLong(userId, loginUser.getUserId());
        if (!Objects.equals(userId, loginUser.getUserId())) {//至允许userId对应的本人和管理员查看指定用户分配的培养计划
            cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        }
        CmsStudyPlanItemInfoVo infoVo =
                cmsStudyPlanItemService.queryUserStudyPlanItemInfo(userId, planId);
        return JsonResult.buildSuccess(infoVo);
    }
}