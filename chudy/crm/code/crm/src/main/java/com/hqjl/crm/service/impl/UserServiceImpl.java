package com.hqjl.crm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hqjl.crm.dao.UserMapper;
import com.hqjl.crm.model.Role;
import com.hqjl.crm.model.User;
import com.hqjl.crm.service.UserService;
import com.hqjl.crm.utils.EncryptUtil;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> listUsersByMap(Map<String, Object> map) {
        return userMapper.listUsersByMap(map);
    }

    @Override
    public long countUsersByMap(Map<String, Object> map) {
        return userMapper.countUsersByMap(map);
    }

    @Override
    public User getUser(int id) {
        return userMapper.getUser(id);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    @Override
    public int updateUser(User user) {
        user.setPassword(EncryptUtil.md5(user.getPassword()));
        user.setTimestamp(new Date());
        return userMapper.updateUser(user);
    }

    @Override
    public void updateUserPasswordByUserName(String userName, String password) {
        userMapper.updateUserPasswordByUserName(userName, password);
    }

    @Override
    public int updateUserStatusById(int id, String status) {
        return userMapper.updateUserStatus(id, status);
    }

    @Override
    public int addUser(User user) {
        user.setPassword(EncryptUtil.md5(user.getPassword()));
        user.setCreateTime(new Date());
        user.setTimestamp(new Date());
        return userMapper.insertUser(user);
    }

    @Override
    public int deleteUserById(int id) {
        return userMapper.deleteUserById(id);
    }

}
