package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsUserStudyPlanQueryVo;
import com.fudanuniversity.cms.business.vo.user.*;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;
import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.TrainingScheme;

@Api(tags = "用户")
@RestController
@RequestMapping(path = "/mng/user")
public class UserMngController extends BaseController {

    @Resource
    private CmsUserService cmsUserService;

    @GetMapping(path = "/list")
    public JsonResult<List<CmsUserVo>> queryUserList(@Valid CmsUserQueryVo queryVo, Paging paging) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId());
        List<CmsUserVo> userList = cmsUserService.queryUserList(queryVo, paging);
        return JsonResult.buildSuccess(userList);
    }

    @Operation(summary = "管理员查询培养计划未分配用户列表")
    @GetMapping("/allocation/list")
    public JsonResult<List<CmsUserVo>> queryAllocationAvailableUserList(@Valid CmsUserStudyPlanQueryVo queryVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), TrainingScheme);
        List<CmsUserVo> userVoList = cmsUserService.queryAvailableAllocationUserList(queryVo);
        return JsonResult.buildSuccess(userVoList);
    }

    @GetMapping(path = "/paging")
    public JsonResult<List<CmsUserVo>> queryPagingResult(@Valid CmsUserQueryVo queryVo, Paging paging) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId());
        PagingResult<CmsUserVo> pagingResult = cmsUserService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @GetMapping(path = "/detail")
    public JsonResult<CmsUserDetailVo> queryUserDetail(@NotEmpty String stuId) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        CmsUserDetailVo userDetailVo = cmsUserService.queryUserDetail(stuId);
        return JsonResult.buildSuccess(userDetailVo);
    }

    //添加新用户，stuId是唯一的标识
    @PostMapping(path = "/add")
    public JsonResult<?> addNewUser(@Valid @RequestBody CmsUserAddVo userAddVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        cmsUserService.saveCmsUser(userAddVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/update")
    public JsonResult<?> updateUser(@Valid @RequestBody CmsUserUpdateMngVo updateVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        cmsUserService.updateCmsUserById(updateVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    public JsonResult<?> delete(@NotNull @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        cmsUserService.deleteCmsUserById(id);
        return JsonResult.buildSuccess();
    }

}