package com.hqjl.crm.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hqjl.crm.Constants;
import com.hqjl.crm.model.User;

@SuppressWarnings("all")
public class SessionUtil implements Constants {

    public static void addAtt(HttpServletRequest request, String key, Object value) {
        request.getSession().setAttribute(key, value);
    }

    public static void removeAtt(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }

    public static String getAtt(HttpServletRequest request, String key) {
        return (String) request.getSession().getAttribute(key);
    }

    public static Object getAttObj(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    public static <T extends Object> T getAtt(HttpServletRequest request, String key, Class<T> objectClass) {
        return (T) request.getSession().getAttribute(key);
    }

    public static String optAtt(HttpServletRequest request, String key, String value) {
        String r = (String) request.getSession().getAttribute(key);
        if (r == null) {
            r = value;
        }
        return r;
    }

    public static void removeLoginUser(HttpServletRequest request) {
        request.getSession().removeAttribute(HQJLUtil.LOGIN_USER_KEY);
    }

    public static void setLoginUser(HttpServletRequest request, User u) {
        request.getSession().setAttribute(HQJLUtil.LOGIN_USER_KEY, u);
    }

    public static User getLoginUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(HQJLUtil.LOGIN_USER_KEY);
    }

    public static void setLoginValidationCode(HttpServletRequest request, String code) {
        request.getSession().setAttribute("login_code", code);
    }

    public static String getLoginValidationCode(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("login_code");
    }

    public static void setRegisterValidationCode(HttpServletRequest request, String code) {
        request.getSession().setAttribute("register_code", code);
    }

    public static String getRegisterValidationCode(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("register_code");
    }

    public static String getCookie(Cookie[] cookies, String key) {
        if (cookies == null)
            return null;
        for (Cookie c : cookies) {
            if (key.equals(c.getName())) {
                return c.getValue();
            }
        }
        return null;
    }

    public static void addCookie(HttpServletResponse response, String key, String value) {
        Cookie c = new Cookie(key, value);
        c.setMaxAge(60 * 60 * 24 * 14);
        c.setPath(HQJLUtil.HOST_ROOT);
        response.addCookie(c);
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(key)) {
                    cookie.setMaxAge(0);
                    cookie.setPath(HQJLUtil.HOST_ROOT);
                    response.addCookie(cookie);
                }
            }
        }
    }

}
