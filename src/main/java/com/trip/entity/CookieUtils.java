/**
 * @since: 2015年3月20日
 * @copyright: copyright (c) 2015,www.linkin.mobi All Rights Reserved.
 */
package com.trip.entity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 平台Cookie操作工具类
 */
public class CookieUtils {

    public static String        COOKIE_PATH      = "/";

    public static final int SCENE_7DAY_EXPIRE_SECONDS  = 604800;
    public static final String  WX_CK_ATRIP  = "wx_ck_atrip";

    public static void main(String[] args) {

    }

    /**
     *
     * 返回cookie路径
     *
     * @author JoeHe
     * @since 2015年8月6日
     * @since 1.0
     *
     * @return
     *
     */
    public static String getCookiePath() {

        return COOKIE_PATH;
    }

    /**
     * 返回指定的cookie值
     *
     * @author JoeHe
     * @since 2014年12月29日
     * @since 1.0
     *
     * @param request
     * @param cookieName
     * @return String
     *
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }





    /**
     * 取消Cookie值
     *
     * @author JoeHe
     * @since 2015年3月20日
     * @since 1.0
     *
     * @param response
     * @param cookieName
     *
     */
    public static void cancelCookie(HttpServletResponse response, String cookieName) {

        if (response == null) {
            return;
        }

        Cookie cookie = new Cookie(cookieName, null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath());

        response.addCookie(cookie);
    }

    /**
     *
     * 向客户端写如Cookie值
     *
     * @author JoeHe
     * @since 2016年4月26日
     * @since 2.0.2
     *
     * @param response
     * @param cookieName
     * @param maxAge
     * @param value
     *
     */
    public static void setCookie(HttpServletResponse response, String cookieName, int maxAge,String value) {

        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);
        cookie.setPath(getCookiePath());
        response.addCookie(cookie);

    }


}
