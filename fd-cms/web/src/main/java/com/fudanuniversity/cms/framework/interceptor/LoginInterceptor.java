package com.fudanuniversity.cms.framework.interceptor;

import com.fudanuniversity.cms.commons.constant.Constants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.exception.ErrorCode;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by tidu at 2021-05-01 22:36:27
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 登录确认验证
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();
        Object loginUserObj = session.getAttribute(Constants.LoginSessionUserKey);
        if (!(loginUserObj instanceof LoginUser)) {
            throw new BusinessException(ErrorCode.LoginErrorCode, "请先登录");
        }
        return true;
    }
}
