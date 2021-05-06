package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsStudyPlanAllocationService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationEditVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationGenerateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationInfoVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationUserEditVo;
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
public class CmsStudyPlanAllocationController extends BaseController {

    @Resource
    private CmsStudyPlanAllocationService cmsStudyPlanAllocationService;

    @Resource
    private CmsUserService cmsUserService;

    /**
     * 管理员为学生分配生成培养计划
     */
    @PostMapping("/generate")
    public JsonResult<?> generateAllocations(@Valid @RequestBody CmsStudyPlanAllocationGenerateVo generateVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanAllocationService.generateUserAllocations(generateVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 管理员变更培养计划分配状态
     */
    @PostMapping("/edit")
    public JsonResult<?> editAllocation(
            @Valid @RequestBody CmsStudyPlanAllocationEditVo editVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanAllocationService.editAllocation(editVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 用户点击完成任务
     */
    @PostMapping("/user/edit")
    public JsonResult<?> changeUserCmsStudyPlanAllocationStatus(
            @Valid @RequestBody CmsStudyPlanAllocationUserEditVo userEditVo) {
        LoginUser loginUser = getLoginUser();
        cmsStudyPlanAllocationService.editUserAllocation(loginUser.getUserId(), userEditVo);
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
        CmsStudyPlanAllocationInfoVo infoVo =
                cmsStudyPlanAllocationService.queryUserAllocationInfo(userId, planId);
        return JsonResult.buildSuccess(infoVo);
    }
}