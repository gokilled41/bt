package com.hqjl.crm.model;

import java.util.ArrayList;
import java.util.List;

public class UserGroups {

    private List<UserGroup> userGroups1 = new ArrayList<UserGroup>(); // read
    private List<UserGroup> userGroups2 = new ArrayList<UserGroup>(); // download

    public List<UserGroup> getUserGroups1() {
        return userGroups1;
    }

    public void setUserGroups1(List<UserGroup> userGroups1) {
        this.userGroups1 = userGroups1;
    }

    public List<UserGroup> getUserGroups2() {
        return userGroups2;
    }

    public void setUserGroups2(List<UserGroup> userGroups2) {
        this.userGroups2 = userGroups2;
    }

    public void makePieces() {
        for (UserGroup userGroup : userGroups1) {
            userGroup.makePieces();
        }
        for (UserGroup userGroup : userGroups2) {
            userGroup.makePieces();
        }
    }

    public void makeNickNames() {
        for (UserGroup userGroup : userGroups1) {
            userGroup.makeNickNames();
        }
        for (UserGroup userGroup : userGroups2) {
            userGroup.makeNickNames();
        }
    }

}
