package com.hqjl.crm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hqjl.crm.dao.AuthMapper;
import com.hqjl.crm.dao.UserMapper;
import com.hqjl.crm.model.Auth;
import com.hqjl.crm.model.User;
import com.hqjl.crm.model.UserGroup;
import com.hqjl.crm.model.UserGroups;
import com.hqjl.crm.model.UserInfo;
import com.hqjl.crm.service.AuthService;
import com.hqjl.crm.utils.ConfigUtil;
import com.hqjl.crm.utils.Util;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserGroups getAllUsers() {
        return getUsers(null);
    }

    @Override
    public UserGroups getUsers(String applyId) {
        List<String> types = ConfigUtil.getBusinessTypes();
        List<User> all = userMapper.listUsers();
        List<Auth> checked = new ArrayList<Auth>();
        Map<String, Auth> checkedMap = new HashMap<String, Auth>();
        if (applyId != null) {
            checked = authMapper.listAuthsByApplyId(applyId);
            if (checked != null && !checked.isEmpty())
                checkedMap = toMap(checked);
        }
        UserGroups userGroups = new UserGroups();
        // read
        List<UserGroup> userGroups1 = new ArrayList<UserGroup>();
        for (String type : types) {
            UserGroup r = new UserGroup();
            r.setBusinessType(type);
            r.setUsers(getUsers(applyId, all, checkedMap, "阅读权限", type));
            if (!r.getUsers().isEmpty()) {
                userGroups1.add(r);
            }
        }
        userGroups.setUserGroups1(userGroups1);
        // download
        List<UserGroup> userGroups2 = new ArrayList<UserGroup>();
        for (String type : types) {
            UserGroup r = new UserGroup();
            r.setBusinessType(type);
            r.setUsers(getUsers(applyId, all, checkedMap, "下载权限", type));
            if (!r.getUsers().isEmpty()) {
                userGroups2.add(r);
            }
        }
        userGroups.setUserGroups2(userGroups2);
        userGroups.makePieces();
        userGroups.makeNickNames();
        return userGroups;
    }

    private List<UserInfo> getUsers(String applyId, List<User> all, Map<String, Auth> checkedMap, String authRole,
            String businessType) {
        List<UserInfo> list = new ArrayList<UserInfo>();
        for (User u : all) {
            if (businessType != null) {
                if (!u.getBusinessType().contains(businessType))
                    continue;
            }
            UserInfo r = new UserInfo();
            r.setUserName(u.getUserName());
            r.setNickName(u.getNickName());
            if (applyId != null)
                r.setChecked(Util.authContains(checkedMap, applyId, authRole, u.getUserName()));
            list.add(r);
        }
        return list;
    }

    private Map<String, Auth> toMap(List<Auth> checked) {
        Map<String, Auth> map = new HashMap<String, Auth>();
        for (Auth r : checked) {
            map.put(Util.toAuthString(r), r);
        }
        return map;
    }

    @Override
    public UserGroups parseUsers(String users) {
        UserGroups ugs = new UserGroups();
        List<UserGroup> ugs1 = ugs.getUserGroups1();
        List<UserGroup> ugs2 = ugs.getUserGroups2();
        List<String> userStrings = Util.splitToList(users, ",");
        Map<String, UserGroup> ugmap1 = new HashMap<String, UserGroup>();
        Map<String, UserGroup> ugmap2 = new HashMap<String, UserGroup>();
        for (String userString : userStrings) {
            List<String> l2 = Util.splitToList(userString, ";");
            String authRole = l2.get(0);
            if (authRole.equals("阅读权限")) {
                String businessType = l2.get(1);
                String userName = l2.get(2);
                String nickName = l2.get(3);

                UserGroup ug = ugmap1.get(businessType);
                if (ug == null) {
                    ug = new UserGroup();
                    ug.setBusinessType(businessType);
                    ugmap1.put(businessType, ug);
                    ugs1.add(ug);
                }

                UserInfo info = new UserInfo();
                info.setUserName(userName);
                info.setNickName(nickName);
                ug.getUsers().add(info);
                String usersNickNames = ug.getUsersNickNames();
                if (usersNickNames.isEmpty()) {
                    usersNickNames += nickName;
                } else {
                    usersNickNames += ("," + nickName);
                }
                ug.setUsersNickNames(usersNickNames);
            } else if (authRole.equals("下载权限")) {
                String businessType = l2.get(1);
                String userName = l2.get(2);
                String nickName = l2.get(3);

                UserGroup ug = ugmap2.get(businessType);
                if (ug == null) {
                    ug = new UserGroup();
                    ug.setBusinessType(businessType);
                    ugmap2.put(businessType, ug);
                    ugs2.add(ug);
                }

                UserInfo info = new UserInfo();
                info.setUserName(userName);
                info.setNickName(nickName);
                ug.getUsers().add(info);
                String usersNickNames = ug.getUsersNickNames();
                if (usersNickNames.isEmpty()) {
                    usersNickNames += nickName;
                } else {
                    usersNickNames += ("," + nickName);
                }
                ug.setUsersNickNames(usersNickNames);
            }
        }
        return ugs;
    }

    @Override
    public void addAuth(String applyId, String users) {
        UserGroups ugs = parseUsers(users);

        List<UserGroup> ugs1 = ugs.getUserGroups1();
        List<UserGroup> ugs2 = ugs.getUserGroups2();
        List<String> list = new ArrayList<String>();
        Map<String, String> map = new HashMap<String, String>();
        for (UserGroup ug : ugs1) {
            String authRole = "阅读权限";
            List<UserInfo> rl1 = ug.getUsers();
            for (UserInfo ri : rl1) {
                String userName = ri.getUserName();
                addAuth(list, map, applyId, authRole, userName);
            }
        }
        for (UserGroup ug : ugs2) {
            String authRole = "下载权限";
            List<UserInfo> rl1 = ug.getUsers();
            for (UserInfo ri : rl1) {
                String userName = ri.getUserName();
                addAuth(list, map, applyId, authRole, userName);
            }
        }
        addAllAuth(list, map, applyId);
    }

    private void addAuth(List<String> list, Map<String, String> map, String applyId, String authRole, String userName) {
        if (!list.contains(userName)) {
            list.add(userName);
            map.put(userName, authRole);
        } else {
            String authRoleTotal = map.get(userName);
            authRoleTotal += ("," + authRole);
            map.put(userName, authRoleTotal);
        }
    }

    private void addAllAuth(List<String> list, Map<String, String> map, String applyId) {
        for (String userName : list) {
            String authRole = map.get(userName);
            addAuth(applyId, authRole, userName);
        }
    }

    private void addAuth(String applyId, String authRole, String userName) {
        Auth r = new Auth();
        r.setApplyId(applyId);
        r.setAuthRole(authRole);
        r.setUserName(userName);
        r.setTimestamp(new Date());
        authMapper.insertAuth(r);
    }

    @Override
    public void updateUsers(String applyId, String users) {
        deleteAuth(applyId);
        addAuth(applyId, users);
    }

    @Override
    public void deleteAuth(String applyId) {
        authMapper.deleteAuthByApplyId(applyId);
    }

}
