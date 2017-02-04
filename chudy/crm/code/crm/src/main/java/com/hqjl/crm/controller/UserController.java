package com.hqjl.crm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hqjl.crm.Constants;
import com.hqjl.crm.model.Role;
import com.hqjl.crm.model.User;
import com.hqjl.crm.service.UserService;
import com.hqjl.crm.utils.ControllerUtil;
import com.hqjl.crm.utils.SessionUtil;

@Controller
@RequestMapping("/user")
public class UserController {

    protected static final Log logger_ = LogFactory.getLog(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("listUser")
    public void listUser(HttpServletRequest request, HttpServletResponse response, Integer start, Integer size,
            String name, String callback) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("size", size);
        map.put("start", start);

        List<User> list = userService.listUsersByMap(map);
        long count = userService.countUsersByMap(map);
        ControllerUtil.responseList(response, callback, list, count, size);
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public void addUser(HttpServletRequest request, HttpServletResponse response, User user, String callback) {
        int r = userService.addUser(user);
        ControllerUtil.responseInt(response, callback, r);
    }

    @RequestMapping(value = "getUser", method = RequestMethod.POST)
    public void getUser(HttpServletRequest request, HttpServletResponse response, int id, String callback) {
        User r = userService.getUser(id);
        ControllerUtil.responseObject(response, callback, r);
    }

    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    public void updateUser(HttpServletRequest request, HttpServletResponse response, User user, String callback) {
        int r = userService.updateUser(user);
        ControllerUtil.responseInt(response, callback, r);
    }

    @RequestMapping("deleteUser")
    public void deleteUser(HttpServletRequest request, HttpServletResponse response, int id, String callback) {
        int r = 0;
        try {
            r = userService.deleteUserById(id);
        } catch (Exception e) {
            if (logger_.isErrorEnabled()) {
                logger_.error("Cannot delete user.", e);
            }
        }
        ControllerUtil.responseInt(response, callback, r);
    }

    @RequestMapping("freezeUser")
    public void freezeUser(HttpServletRequest request, HttpServletResponse response, int id, String callback) {
        int r = 0;
        try {
            r = userService.updateUserStatusById(id, Constants.UserStatusFreezed);
        } catch (Exception e) {
            if (logger_.isErrorEnabled()) {
                logger_.error("Cannot freeze user.", e);
            }
        }
        ControllerUtil.responseInt(response, callback, r);
    }

    @RequestMapping("unfreezeUser")
    public void unfreezeUser(HttpServletRequest request, HttpServletResponse response, int id, String callback) {
        int r = 0;
        try {
            r = userService.updateUserStatusById(id, Constants.UserStatusNormal);
        } catch (Exception e) {
            if (logger_.isErrorEnabled()) {
                logger_.error("Cannot unfreeze user.", e);
            }
        }
        ControllerUtil.responseInt(response, callback, r);
    }

    @RequestMapping(value = "getCurrentUser", method = RequestMethod.POST)
    public void getCurrentUser(HttpServletRequest request, HttpServletResponse response, String callback) {
        User loginUser = SessionUtil.getLoginUser(request);
        if (loginUser != null) {
            String userName = loginUser.getUserName();
            User r = userService.getUserByUserName(userName);
            ControllerUtil.responseObject(response, callback, r);
        } else {
            if (logger_.isErrorEnabled()) {
                logger_.error("User has not logged in.");
            }
            ControllerUtil.responseBoolean(response, callback, false);
        }
    }

    @RequestMapping(value = "getRole", method = RequestMethod.POST)
    public void getRole(HttpServletRequest request, HttpServletResponse response, String callback) {
        User loginUser = SessionUtil.getLoginUser(request);
        if (loginUser != null) {
            String userName = loginUser.getUserName();
            // Role r = userService.getRoleByUserName(userName);
            Role r = null;
            ControllerUtil.responseObject(response, callback, r);
        } else {
            if (logger_.isErrorEnabled()) {
                logger_.error("User has not logged in.");
            }
            ControllerUtil.responseBoolean(response, callback, false);
        }
    }

}
