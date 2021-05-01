package com.fudanuniversity.cms.web.controller;

import com.fudanuniversity.cms.combi.service.CmsUserAccountService;
import com.fudanuniversity.cms.combi.service.CmsUserService;
import com.fudanuniversity.cms.combi.vo.CmsUserAccountLoginVo;
import com.fudanuniversity.cms.combi.vo.CmsUserAccountResetPasswordVo;
import com.fudanuniversity.cms.combi.vo.CmsUserMngVo;
import com.fudanuniversity.cms.commons.constant.Constants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.framework.util.Webmvc;
import com.fudanuniversity.cms.inner.entity.CmsUser;
import com.fudanuniversity.cms.inner.entity.CmsUserAccount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;

@RestController
@RequestMapping(path = "/user")
public class UserController extends BaseController {

    @Resource
    private CmsUserService cmsUserService;

    @Resource
    private CmsUserAccountService cmsUserAccountService;

    @RequestMapping(path = "/login")
    public JsonResult<?> login(@Valid CmsUserAccountLoginVo userAccountVo) {
        Object loginUserObj = Webmvc.session().getAttribute(Constants.LoginSessionUserKey);
        if (loginUserObj instanceof LoginUser) {
            throw new BusinessException("当前状态已登录");
        }
        CmsUserAccount userAccount = cmsUserAccountService.loginAccount(userAccountVo);
        LoginUser loginUser = new LoginUser();
        loginUser.setStuId(userAccount.getStuId());
        loginUser.setLoginTime(new Date());
        Webmvc.session().setAttribute(Constants.LoginSessionUserKey, loginUser);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/reset")
    public JsonResult<?> reset(@Valid CmsUserAccountResetPasswordVo resetPasswordVo) {
        cmsUserAccountService.resetAccountPassword(resetPasswordVo);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/all")
    public JsonResult<?> getAllUsers(Paging paging) {
        PagingResult<CmsUser> allUsers = cmsUserService.getAllUsers(paging);
        return JsonResult.buildSuccess(allUsers);
    }

    //添加新用户，stuId是唯一的标识
    @PostMapping(path = "/add")
    public JsonResult<?> addNewUser(@Valid CmsUserMngVo userAddVo) {
        //当前登录者权限校验
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        //添加新用户
        cmsUserService.saveCmsUser(userAddVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/update")
    public JsonResult<?> updateUser(@Valid CmsUserMngVo userAddVo) {
        //当前登录者权限校验
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        //添加新用户
        cmsUserService.updateCmsUserById(userAddVo);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/delete")
    public JsonResult<?> delete(@NotNull @Min(1L) Long userId) {
        //当前登录者权限校验
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        //删除用户
        cmsUserService.deleteCmsUserById(userId);
        return JsonResult.buildSuccess();
    }

}