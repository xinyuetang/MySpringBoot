package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsStudyPlanAllocationService;
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
import java.util.List;

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
    public JsonResult<?> generateCmsStudyPlanAllocation(@Valid @RequestBody CmsStudyPlanAllocationGenerateVo generateVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanAllocationService.generateCmsStudyPlanAllocation(generateVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id更新处理
     */
    @PostMapping("/update")
    public JsonResult<?> updateCmsStudyPlanAllocationById(@Valid @RequestBody CmsStudyPlanAllocationUpdateVo updateVo) {
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
    public JsonResult<?> queryPagingResult(
            @Valid CmsStudyPlanAllocationQueryVo queryVo, @Valid Paging paging) {
        PagingResult<CmsStudyPlanAllocationVo> pagingResult =
                cmsStudyPlanAllocationService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    /**
     * 用户查询自己的培养计划
     */
    @GetMapping("/info")
    public JsonResult<?> queryUserCmsStudyPlanAllocation() {
        LoginUser loginUser = getLoginUser();
        CmsStudyPlanAllocationInfoVo infoVo =
                cmsStudyPlanAllocationService.queryUserCmsStudyPlanAllocation(loginUser.getUserId());
        return JsonResult.buildSuccess(infoVo);
    }
}