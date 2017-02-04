package com.hqjl.crm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hqjl.crm.model.User;
import com.hqjl.crm.service.UserService;
import com.hqjl.crm.utils.HTTPUtil;
import com.hqjl.crm.utils.SessionUtil;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @RequestMapping("/page/**")
    public String page(HttpServletRequest request) {
        String prefix = "page";
        String suffix = ".shtml";

        // get page value
        String url = request.getRequestURI();

        int index = url.indexOf(prefix);
        String page = url.substring(index + prefix.length(), url.indexOf(suffix));

        // for admin pages
        if (page != null && page.startsWith("/admin")) {
            //check if login
            User u = SessionUtil.getLoginUser(request);
            if (needRedirect(request, u)) {
                SessionUtil.addAtt(request, "redirectUrl", HTTPUtil.getRequestURLWithParameters(request));
                return "/admin/login";
            } else {
                if (page.contains("login")) {
                    return "/admin/index";
                }
                return page;
            }
        }

        // for auto login
        return page;
    }

    private boolean needRedirect(HttpServletRequest r, User u) {
        String id = (u == null ? "" : "" + u.getId());
        String uids = r.getParameter("uid");
        SessionUtil.removeAtt(r, "loginUserName");
        if (uids != null) { // specified user
            if (!uids.equals(id)) {
                int uid = Integer.valueOf(uids);
                if (!id.isEmpty())
                    SessionUtil.removeLoginUser(r);
                SessionUtil.addAtt(r, "loginUserName", userService.getUser(uid).getUserName());
                return true;
            } else {
                return false;
            }
        } else { // not specified user
            if (id.isEmpty()) { // need login
                return true;
            } else { // already login
                return false;
            }
        }
    }
}
