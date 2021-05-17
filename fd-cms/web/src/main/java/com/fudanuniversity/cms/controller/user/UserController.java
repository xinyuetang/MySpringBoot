package com.fudanuniversity.cms.controller.user;

import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsUserAccountService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.user.*;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import com.fudanuniversity.cms.framework.util.Webmvc;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.entity.CmsUserAccount;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;

@Api(tags = "用户")
@RestController
@RequestMapping(path = "/u/user")
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
    public JsonResult<Map<String, Object>> login(@Valid CmsUserAccountLoginVo userAccountVo) {
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

    @PostMapping(path = "/logout")
    public JsonResult<?> logout() {
        Object loginUserObj = Webmvc.session().getAttribute(CmsConstants.LoginSessionUserKey);
        if (!(loginUserObj instanceof LoginUser)) {
            throw new BusinessException("请先登录");
        }
        Webmvc.session().removeAttribute(CmsConstants.LoginSessionUserKey);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/reset")
    public JsonResult<?> reset(@Valid @RequestBody CmsUserAccountResetPasswordVo resetPasswordVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserAccountService.resetAccountPassword(loginUser.getStuId(), resetPasswordVo);
        return JsonResult.buildSuccess();
    }


    @GetMapping(path = "/detail")
    public JsonResult<CmsUserDetailVo> queryUserDetail() {
        LoginUser loginUser = getLoginUser();
        CmsUserDetailVo userDetailVo = cmsUserService.queryUserDetail(loginUser.getStuId());
        return JsonResult.buildSuccess(userDetailVo);
    }

    @PostMapping(path = "/update")
    public JsonResult<?> updateUser(@Valid @RequestBody CmsUserUpdateVo updateVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.updateCmsUserById(loginUser.getUserId(), updateVo);
        return JsonResult.buildSuccess();
    }
}