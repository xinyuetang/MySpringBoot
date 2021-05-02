package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.commons.constant.Constants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.framework.util.Webmvc;

/**
 * Created by tidu at 2021-05-02 01:43:58
 */
public abstract class BaseController {

    public LoginUser getLoginUser() {
        Object loginUserObj = Webmvc.session().getAttribute(Constants.LoginSessionUserKey);
        if (!(loginUserObj instanceof LoginUser)) {
            throw new BusinessException("请先登录");
        }
        return (LoginUser) loginUserObj;
    }
}
