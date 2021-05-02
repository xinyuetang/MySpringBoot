package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsBulletinService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.bulletin.*;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Bulletin;

@RestController
@RequestMapping(path = "/bulletin")
public class BulletinController extends BaseController {

    @Resource
    private CmsBulletinService cmsBulletinService;

    @Resource
    private CmsUserService cmsUserService;

    @PostMapping(path = "/add")
    public JsonResult<?> addNewBulletin(@RequestBody @Valid CmsBulletinAddVo addVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Bulletin);
        cmsBulletinService.addNewBulletin(addVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/update")
    public JsonResult<?> editBulletin(@RequestBody @Valid CmsBulletinEditVo editVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Bulletin);
        cmsBulletinService.editBulletin(editVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    public JsonResult<?> deleteBulletin(@NotNull Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Bulletin);
        cmsBulletinService.deleteCmsBulletinById(id);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/read")
    public JsonResult<?> readBulletin(@NotNull Long id) {
        LoginUser loginUser = getLoginUser();
        cmsBulletinService.readBulletin(loginUser.getUserId(), id);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/paging")
    public JsonResult<?> queryPagingResult(CmsBulletinQueryVo queryVo, Paging paging) {
        LoginUser loginUser = getLoginUser();
        PagingResult<CmsBulletinVo> pagingResult = cmsBulletinService.queryPagingResult(loginUser.getUserId(), queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @GetMapping(path = "/state")
    public JsonResult<?> queryCmsBulletinReadState(CmsBulletinStateQueryVo stateQueryVo) {
        LoginUser loginUser = getLoginUser();
        CmsBulletinStateVo bulletinStateVo = cmsBulletinService.queryCmsBulletinReadState(loginUser.getUserId(), stateQueryVo);
        return JsonResult.buildSuccess(bulletinStateVo);
    }
}
