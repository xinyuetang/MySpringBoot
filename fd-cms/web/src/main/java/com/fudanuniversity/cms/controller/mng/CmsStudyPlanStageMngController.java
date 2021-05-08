package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsStudyPlanStageService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;

/**
 * CmsStudyPlanStageController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 18:08:08
 */
@RestController
@RequestMapping("/mng/study/plan/stage")
public class CmsStudyPlanStageMngController extends BaseController {

    @Resource
    private CmsStudyPlanStageService cmsStudyPlanStageService;

    @Resource
    private CmsUserService cmsUserService;

    /**
     * 保存处理
     */
    @PostMapping("/save")
    public JsonResult<?> saveCmsStudyPlanStage(@Valid @RequestBody CmsStudyPlanStageAddVo addVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanStageService.saveCmsStudyPlanStage(addVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id更新处理
     */
    @PostMapping("/update")
    public JsonResult<?> updateCmsStudyPlanStageById(@Valid @RequestBody CmsStudyPlanStageUpdateVo updateVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanStageService.updateCmsStudyPlanStageById(updateVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 批量更新处理
     */
    @PostMapping("/edit")
    public JsonResult<?> updateCmsStudyPlanStageById(@Valid @RequestBody List<CmsStudyPlanStageEditVo> editVoList) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanStageService.editCmsStudyPlanStages(editVoList);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id删除处理
     */
    @PostMapping("/delete")
    public JsonResult<?> deleteCmsStudyPlanStageById(@NotNull @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        cmsStudyPlanStageService.deleteCmsStudyPlanStageById(id);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据条件查询信息列表
     */
    @GetMapping("/list")
    public JsonResult<?> queryCmsStudyPlanStageList(@Valid CmsStudyPlanStageQueryVo queryVo) {
        List<CmsStudyPlanStageVo> pagingResult = cmsStudyPlanStageService.queryCmsStudyPlanStageList(queryVo);
        return JsonResult.buildSuccess(pagingResult);
    }
}