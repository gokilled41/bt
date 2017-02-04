package com.hqjl.crm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hqjl.crm.model.UserGroups;
import com.hqjl.crm.service.AuthService;
import com.hqjl.crm.utils.ControllerUtil;

@Controller
@RequestMapping("/auth")
public class AuthController {

    protected static final Log logger_ = LogFactory.getLog(AuthController.class);

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "getAllUsers", method = RequestMethod.POST)
    public void getAllUsers(HttpServletRequest request, HttpServletResponse response, String callback) {
        UserGroups r = authService.getAllUsers();
        ControllerUtil.responseObject(response, callback, r);
    }

    @RequestMapping(value = "parseUsers", method = RequestMethod.POST)
    public void parseUsers(HttpServletRequest request, HttpServletResponse response, String users, String callback) {
        UserGroups r = authService.parseUsers(users);
        ControllerUtil.responseObject(response, callback, r);
    }

    @RequestMapping(value = "getUsers", method = RequestMethod.POST)
    public void getUsers(HttpServletRequest request, HttpServletResponse response, String applyId, String callback) {
        UserGroups r = authService.getUsers(applyId);
        ControllerUtil.responseObject(response, callback, r);
    }

    @RequestMapping(value = "updateUsers", method = RequestMethod.POST)
    public void updateUsers(HttpServletRequest request, HttpServletResponse response, String applyId, String users,
            String callback) {
        authService.updateUsers(applyId, users);
        ControllerUtil.responseBoolean(response, callback, true);
    }

}
