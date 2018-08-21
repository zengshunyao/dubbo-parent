package com.funi.distributedcomputer.protal.intercept;

import com.alibaba.fastjson.JSONObject;
import com.funi.distributedcomputer.dubbo.user.IUserCoreService;
import com.funi.distributedcomputer.dubbo.user.constants.ResponseCodeEnum;
import com.funi.distributedcomputer.dubbo.user.dto.CheckAuthRequest;
import com.funi.distributedcomputer.dubbo.user.dto.CheckAuthResponse;
import com.funi.distributedcomputer.protal.constants.Constants;
import com.funi.distributedcomputer.protal.controller.BaseController;
import com.funi.distributedcomputer.protal.controller.support.Anonymous;
import com.funi.distributedcomputer.protal.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class LoginIntercept extends HandlerInterceptorAdapter {


    @Autowired
    IUserCoreService userCoreService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Object action = handlerMethod.getBean();
        if (!(action instanceof BaseController)) {
            throw new Exception("异常");
        }

        BaseController baseController = (BaseController) action;

        if (isAnonymous(handlerMethod)) {
            return true;
        }
        String accessToken = CookieUtil.getCookieValue(request, Constants.ACCESS_TOKEN);
        //没有登录的情况下
        if (StringUtils.isEmpty(accessToken)) {
            if (CookieUtil.isAjax(request)) {
                JSONObject object = new JSONObject();
                object.put("code", "-1");
                object.put("msg", "没有登录");
                response.getWriter().write(object.toJSONString());
                response.flushBuffer();
                return false;
            }
            response.sendRedirect("login.shtml");
            return false;
        }

        CheckAuthRequest checkAuthRequest = new CheckAuthRequest();
        checkAuthRequest.setToken(accessToken);

        CheckAuthResponse checkAuthResponse = userCoreService.checkAuth(checkAuthRequest);

        if (ResponseCodeEnum.SYS_SUCCESS.getCode().equals(checkAuthResponse.getCode())) {
            baseController.setUid(checkAuthResponse.getUid());
            return true;
        }
        return false;
//        return super.preHandle(request, response, handler);
    }

    private boolean isAnonymous(HandlerMethod handlerMethod) {
        Object action = handlerMethod.getBean();
        Class<?> clazz = action.getClass();
        if (null != clazz.getAnnotation(Anonymous.class)) {
            return true;
        }
        Method method = handlerMethod.getMethod();
        return method.getAnnotation(Anonymous.class) != null;
    }
}
