package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsUserAccountService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.user.*;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.framework.util.Webmvc;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.entity.CmsUserAccount;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;

@Api(tags = "用户")
@RestController
@RequestMapping(path = "/user")
public class UserController extends BaseController {

    @Resource
    private CmsUserService cmsUserService;

    @Resource
    private CmsUserComponent cmsUserComponent;

    @Resource
    private CmsUserAccountService cmsUserAccountService;

    @RequestMapping(path = "/info")
    public JsonResult<?> info() {
        LoginUser loginUser = getLoginUser();
        CmsLoginUserVo loginUserVo = new CmsLoginUserVo();
        loginUserVo.setUserId(loginUser.getUserId());
        loginUserVo.setStuId(loginUser.getStuId());
        loginUserVo.setName(loginUser.getName());
        loginUserVo.setRoleId(loginUser.getRoleId());
        loginUserVo.setLoginTime(loginUser.getLoginTime());
        return JsonResult.buildSuccess(loginUserVo);
    }

    @RequestMapping(path = "/login")
    //@PostMapping(path = "/login")
    public JsonResult<?> login(@Valid CmsUserAccountLoginVo userAccountVo) {
        /*
        更换用户登录后替换老用户session
        Object loginUserObj = Webmvc.session().getAttribute(CmsConstants.LoginSessionUserKey);
        if (loginUserObj instanceof LoginUser) {
            throw new BusinessException("当前状态已登录");
        }*/
        CmsUserAccount userAccount = cmsUserAccountService.loginAccount(userAccountVo);
        String stuId = userAccount.getStuId();
        CmsUser cmsUser = cmsUserComponent.queryUser(stuId);
        if (cmsUser == null) {
            throw new BusinessException("当前用户不存在，请联系管理员");
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(cmsUser.getId());
        loginUser.setStuId(stuId);
        loginUser.setRoleId(cmsUser.getRoleId());
        loginUser.setName(cmsUser.getName());
        loginUser.setLoginTime(new Date());
        Webmvc.session().setAttribute(CmsConstants.LoginSessionUserKey, loginUser);
        Map<String, Object> welcome = Maps.newLinkedHashMap();
        welcome.put("userId", loginUser.getUserId());
        welcome.put("stuId", loginUser.getStuId());
        welcome.put("name", loginUser.getName());
        return JsonResult.buildSuccess(welcome);
    }

    @PostMapping(path = "/reset")
    public JsonResult<?> reset(@Valid @RequestBody CmsUserAccountResetPasswordVo resetPasswordVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserAccountService.resetAccountPassword(loginUser.getStuId(), resetPasswordVo);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/list")
    public JsonResult<?> queryUserList(@Valid CmsUserQueryVo queryVo, Paging paging) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId());
        List<CmsUserVo> userList = cmsUserService.queryUserList(queryVo, paging);
        return JsonResult.buildSuccess(userList);
    }

    @GetMapping(path = "/paging")
    public JsonResult<?> queryPagingResult(@Valid CmsUserQueryVo queryVo, Paging paging) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId());
        PagingResult<CmsUserVo> pagingResult = cmsUserService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @GetMapping(path = "/detail")
    public JsonResult<?> queryUserDetail(@NotEmpty String stuId) {
        LoginUser loginUser = getLoginUser();
        //如果是管理员可以查所有的用户，如果是普通用户只可以查自己的数据
        if (!Objects.equals(stuId, loginUser.getStuId())) {
            cmsUserService.checkManagePrivilege(loginUser.getStuId(), Administrator);
        }
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
    public JsonResult<?> updateUser(@Valid @RequestBody CmsUserUpdateVo updateVo) {
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