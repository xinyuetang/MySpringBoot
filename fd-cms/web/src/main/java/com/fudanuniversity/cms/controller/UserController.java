package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsUserAccountService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.user.CmsUserAccountLoginVo;
import com.fudanuniversity.cms.business.vo.user.CmsUserAccountResetPasswordVo;
import com.fudanuniversity.cms.business.vo.user.CmsUserMngVo;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.commons.validation.ValidGroup;
import com.fudanuniversity.cms.commons.validation.group.Update;
import com.fudanuniversity.cms.framework.util.Webmvc;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.entity.CmsUserAccount;
import com.google.common.collect.Maps;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;

@RestController
@RequestMapping(path = "/user")
public class UserController extends BaseController {

    @Resource
    private CmsUserService cmsUserService;

    @Resource
    private CmsUserComponent cmsUserComponent;

    @Resource
    private CmsUserAccountService cmsUserAccountService;

    @RequestMapping(path = "/login") //TODO
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
        loginUser.setName(cmsUser.getName());
        loginUser.setLoginTime(new Date());
        Webmvc.session().setAttribute(CmsConstants.LoginSessionUserKey, loginUser);
        Map<String, Object> welcome = Maps.newLinkedHashMap();
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

    @GetMapping(path = "/all")
    public JsonResult<?> getAllUsers(Paging paging) {
        //当前登录者权限校验
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        PagingResult<CmsUser> allUsers = cmsUserService.getAllUsers(paging);
        return JsonResult.buildSuccess(allUsers);
    }

    //添加新用户，stuId是唯一的标识
    @PostMapping(path = "/add")
    public JsonResult<?> addNewUser(@Valid @RequestBody CmsUserMngVo userAddVo) {
        //当前登录者权限校验
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        //添加新用户
        cmsUserService.saveCmsUser(userAddVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/update")
    @ValidGroup(Update.class)
    public JsonResult<?> updateUser(@Valid @RequestBody CmsUserMngVo userAddVo) {
        //当前登录者权限校验
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        //添加新用户
        cmsUserService.updateCmsUserById(userAddVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    public JsonResult<?> delete(@NotNull @Min(1L) Long id) {
        //当前登录者权限校验
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        //删除用户
        cmsUserService.deleteCmsUserById(id);
        return JsonResult.buildSuccess();
    }

}