package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsBulletinService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinAddVo;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinEditVo;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinQueryVo;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Bulletin;

@Api(tags = "管理员 - 通知")
@RestController
@RequestMapping(path = "/mng/bulletin")
public class BulletinMngController extends BaseController {

    @Resource
    private CmsBulletinService cmsBulletinService;

    @Resource
    private CmsUserService cmsUserService;

    @Operation(summary = "管理员新增通知")
    @PostMapping(path = "/add")
    public JsonResult<?> addNewBulletin(@RequestBody @Valid CmsBulletinAddVo addVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Bulletin);
        cmsBulletinService.addNewBulletin(addVo);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员修改通知")
    @PostMapping(path = "/update")
    public JsonResult<?> editBulletin(@RequestBody @Valid CmsBulletinEditVo editVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Bulletin);
        cmsBulletinService.editBulletin(editVo);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员删除通知")
    @PostMapping(path = "/delete")
    public JsonResult<?> deleteBulletin(@NotNull Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Bulletin);
        cmsBulletinService.deleteCmsBulletinById(id);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员查看通知分页列表")
    @GetMapping(path = "/paging")
    public JsonResult<?> queryPagingResult(@Valid CmsBulletinQueryVo queryVo, Paging paging) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Bulletin);
        PagingResult<CmsBulletinVo> pagingResult = cmsBulletinService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}
