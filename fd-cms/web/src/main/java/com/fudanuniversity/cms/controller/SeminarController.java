package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsSeminarService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarAddVo;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarQueryVo;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarUpdateVo;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarVo;
import com.fudanuniversity.cms.commons.enums.UserRoleEnum;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping(path = "/seminar")
public class SeminarController extends BaseController {

    @Resource
    private CmsUserService cmsUserService;

    @Resource
    private CmsSeminarService cmsSeminarService;

    @PostMapping(path = "/add")
    public JsonResult<?> addNewSeminar(@Valid @RequestBody CmsSeminarAddVo seminarVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), UserRoleEnum.Seminar);
        cmsSeminarService.addNewSeminar(seminarVo);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/update")
    public JsonResult<?> addSeminarLink(@Valid @RequestBody CmsSeminarUpdateVo seminarUpdateVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), UserRoleEnum.Seminar);
        cmsSeminarService.updateSeminarById(seminarUpdateVo);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/delete")
    public JsonResult<?> deleteSeminar(@NotNull @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), UserRoleEnum.Seminar);
        cmsSeminarService.deleteCmsSeminarById(id);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/paging")
    public JsonResult<?> queryPagingResult(@Valid CmsSeminarQueryVo queryVo, Paging paging) {
        PagingResult<CmsSeminarVo> pagingResult = cmsSeminarService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}
