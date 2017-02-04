package com.hqjl.crm.model;

import java.util.ArrayList;
import java.util.List;

import com.hqjl.crm.utils.Util;

public class UserGroup {

    private String businessType;
    private String usersNickNames = "";
    private List<UserInfo> users = new ArrayList<UserInfo>();
    private List<List<UserInfo>> usersPieces = new ArrayList<List<UserInfo>>();

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getUsersNickNames() {
        return usersNickNames;
    }

    public void setUsersNickNames(String usersNickNames) {
        this.usersNickNames = usersNickNames;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

    public List<List<UserInfo>> getUsersPieces() {
        return usersPieces;
    }

    public void setUsersPieces(List<List<UserInfo>> usersPieces) {
        this.usersPieces = usersPieces;
    }

    public void makePieces() {
        int size = users.size();
        List<UserInfo> list = new ArrayList<UserInfo>();
        int i = 0;
        int c = 0;
        for (UserInfo userInfo : users) {
            list.add(userInfo);
            i++;
            c++;
            if (i == 4 || c == size) {
                usersPieces.add(list);
                list = new ArrayList<UserInfo>();
            }
        }
    }

    public void makeNickNames() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < users.size(); i++) {
            UserInfo info = users.get(i);
            if (info.isChecked()) {
                sb.append(info.getNickName());
                sb.append(",");
            }
        }
        usersNickNames = Util.cutLast(sb.toString(), 1);
    }

}
