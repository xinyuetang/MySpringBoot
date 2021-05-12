package com.fudanuniversity.cms.controller.user;

import com.fudanuniversity.cms.business.service.CmsBulletinService;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinQueryVo;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinStateQueryVo;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinStateVo;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "用户 - 通知")
@RestController
@RequestMapping(path = "/u/bulletin")
public class BulletinController extends BaseController {

    @Resource
    private CmsBulletinService cmsBulletinService;

    @Operation(summary = "用户标记通知已读")
    @RequestMapping(path = "/read")
    public JsonResult<?> readBulletin(@NotNull Long id) {
        LoginUser loginUser = getLoginUser();
        cmsBulletinService.readBulletin(loginUser.getUserId(), id);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "用户标记所有通知已读")
    @RequestMapping(path = "/read/all")
    public JsonResult<?> readAllBulletin() {
        LoginUser loginUser = getLoginUser();
        cmsBulletinService.readAllBulletin(loginUser.getUserId());
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "用户查询通知列表")
    @GetMapping(path = "/paging")
    public JsonResult<?> queryPagingResult(@Valid CmsBulletinQueryVo queryVo, Paging paging) {
        LoginUser loginUser = getLoginUser();
        PagingResult<CmsBulletinVo> pagingResult = cmsBulletinService.queryPagingResult(loginUser.getUserId(), queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @Operation(summary = "用户查询通知已读未读情况")
    @GetMapping(path = "/state")
    public JsonResult<?> queryCmsBulletinReadState(@Valid CmsBulletinStateQueryVo stateQueryVo) {
        LoginUser loginUser = getLoginUser();
        CmsBulletinStateVo bulletinStateVo = cmsBulletinService.queryCmsBulletinReadState(loginUser.getUserId(), stateQueryVo);
        return JsonResult.buildSuccess(bulletinStateVo);
    }
}
