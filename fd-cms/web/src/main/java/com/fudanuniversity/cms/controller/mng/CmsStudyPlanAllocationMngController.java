package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsStudyPlanAllocationService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationInfoVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationOverviewVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.commons.util.ValueUtils;
import com.fudanuniversity.cms.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;

/**
 * CmsStudyPlanAllocationController
 * <p>
 * Created by Xinyue.Tang at 2021-05-07 11:39:06
 */
@RestController
@RequestMapping("/mng/study/plan/allocation")
public class CmsStudyPlanAllocationMngController extends BaseController {

    @Resource
    private CmsStudyPlanAllocationService cmsStudyPlanAllocationService;

    @Resource
    private CmsUserService cmsUserService;

    @Operation(summary = "管理员查询培养计划任务完成情况")
    @GetMapping("/info/list")
    public JsonResult<?> queryAllocationInfoList(
            @NotNull(message = "培养计划id不能为空") @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        List<CmsStudyPlanAllocationInfoVo> infoVoList = cmsStudyPlanAllocationService.queryAllocationInfoList(id);
        return JsonResult.buildSuccess(infoVoList);
    }


    /**
     * 根据id删除处理
     */
    @PostMapping("/delete")
    public JsonResult<?> deleteCmsStudyPlanAllocationById(@NotNull @Min(1L) Long id, @NotNull @Min(1L) Long userId) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanAllocationService.deleteCmsStudyPlanAllocationById(id, userId);
        return JsonResult.buildSuccess();
    }

    /**
     * 管理员或用户查看培养计划任务完成情况
     */
    @GetMapping("/info")
    public JsonResult<?> queryAllocationInfo(
            Long userId,
            @NotNull(message = "培养计划id不能为空") @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        userId = ValueUtils.defaultLong(userId, loginUser.getUserId());
        if (!Objects.equals(userId, loginUser.getUserId())) {//至允许userId对应的本人和管理员查看指定用户分配的培养计划
            cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        }
        CmsStudyPlanAllocationInfoVo allocationInfoVo = cmsStudyPlanAllocationService.queryAllocationInfo(id, userId);
        return JsonResult.buildSuccess(allocationInfoVo);
    }

    /**
     * 管理员或用户预览用户分配的培养计划
     */
    @GetMapping("/overview")
    public JsonResult<?> queryUserCmsStudyPlanOverview(
            Long userId,
            @NotNull(message = "培养计划id不能为空") @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        userId = ValueUtils.defaultLong(userId, loginUser.getUserId());
        if (!Objects.equals(userId, loginUser.getUserId())) {//至允许userId对应的本人和管理员查看指定用户分配的培养计划
            cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        }
        CmsStudyPlanAllocationOverviewVo overview = cmsStudyPlanAllocationService.queryUserAllocationOverview(userId, id);
        return JsonResult.buildSuccess(overview);
    }
}