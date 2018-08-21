package com.funi.distributedcomputer.protal.utils;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 */
public class CookieUtil {
    static Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    private CookieUtil() {
    }

    /**
     * 添加cookie
     *
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     *
     * @param response
     * @param name
     */
    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie uid = new Cookie(name, null);
        uid.setPath("/");
        uid.setMaxAge(0);
        response.addCookie(uid);
    }

    /**
     * 获取cookie值
     *
     * @param request
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        try {
            Cookie cookies[] = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(cookieName)) {
                        return cookie.getValue();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
        return null;
    }

    public static Boolean isAjax(HttpServletRequest request) {
        String xReq = request.getHeader("x-requested-with");
        if (StringUtils.isNotBlank(xReq) && "XMLHttpRequest".equalsIgnoreCase(xReq)) {
            // 是ajax异步请求
            return true;
        }
        return false;
    }
}
