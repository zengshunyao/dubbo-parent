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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author zengshunyao
 */
public class LoginIntercept extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(LoginIntercept.class);

    @Autowired
    IUserCoreService userCoreService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.封装参数
        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final Object controller = handlerMethod.getBean();
        if (!(controller instanceof BaseController)) {
            throw new Exception("异常");
        }

        //2.检查是否可匿名访问
        if (this.isAnonymous(handlerMethod)) {
            return true;
        }

        //3.检查是否登录
        final String accessToken = CookieUtil.getCookieValue(request, Constants.ACCESS_TOKEN);
        //3.1没有token的情况下
        if (StringUtils.isEmpty(accessToken)) {
            if (response.isCommitted()) {
                logger.warn("异常：已经输出！");
            }
            //异步提交
            if (CookieUtil.isAjax(request)) {
                JSONObject object = new JSONObject();
                object.put("code", "-1");
                object.put("msg", "没有登录");
                response.getWriter().write(object.toJSONString());
                response.flushBuffer();
                return false;
            }
            // 普通表单提交
            // 重定向登录页面
            response.sendRedirect(Constants.LOGIN_INDEX);
            return false;
        }

        //3.2存在token，则检查登录状态
        //3.2.1请求服务检查登录状态
        final CheckAuthRequest checkAuthRequest = new CheckAuthRequest();
        checkAuthRequest.setToken(accessToken);

        final CheckAuthResponse checkAuthResponse = userCoreService.checkAuth(checkAuthRequest);
        //3.2.2根据检查结果 分别处理
        if (ResponseCodeEnum.SYS_SUCCESS.getCode().equals(checkAuthResponse.getCode())) {
            final BaseController baseController = (BaseController) controller;
            //记录到当前线程中
            baseController.setUid(checkAuthResponse.getUid());
            return super.preHandle(request, response, handler);
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //处理完成清理线程栈的user对象
        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final Object controller = handlerMethod.getBean();
        if (!(controller instanceof BaseController)) {
            throw new Exception("异常");
        }

        //当前线程中，用完记得擦屁股
        if (controller instanceof BaseController) {
            final BaseController baseController = BaseController.class.cast(controller);
            baseController.clearUid();
        }
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 是否允许匿名访问
     *
     * @param handlerMethod
     * @return
     */
    private boolean isAnonymous(HandlerMethod handlerMethod) {
        final Object controller = handlerMethod.getBean();
        final Class<?> clazz = controller.getClass();
        if (null != clazz.getAnnotation(Anonymous.class)) {
            return true;
        }
        final Method method = handlerMethod.getMethod();
        return method.getAnnotation(Anonymous.class) != null;
    }
}
