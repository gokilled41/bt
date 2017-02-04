package com.hqjl.crm.service;

import java.util.List;
import java.util.Map;

import com.hqjl.crm.model.User;

public interface UserService {

    public List<User> listUsersByMap(Map<String, Object> map);

    public long countUsersByMap(Map<String, Object> map);

    public User getUser(int id);

    public User getUserByUserName(String userName);

    public int updateUser(User user);

    public void updateUserPasswordByUserName(String userName, String password);

    public int updateUserStatusById(int id, String status);

    public int addUser(User user);

    public int deleteUserById(int id);

}
