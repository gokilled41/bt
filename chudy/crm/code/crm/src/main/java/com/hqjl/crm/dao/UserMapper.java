package com.hqjl.crm.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hqjl.crm.model.User;

public interface UserMapper {

    public List<User> listUsers();

    public List<User> listUsersByMap(Map<String, Object> map);

    public List<User> listUsersByApprovalRole(String approvalRole);

    public List<User> listUsersByAdminRole(String adminRole);

    public long countUsersByMap(Map<String, Object> map);

    public User getUser(@Param("id") int id);

    public User getUserByUserName(@Param("userName") String userName);

    public User getUserByNickName(@Param("nickName") String nickName);

    public int updateUser(User user);

    public int updateUserPasswordByUserName(@Param("userName") String userName, @Param("password") String password);

    public int updateUserTimestamp(@Param("id") int id, @Param("timestamp") Date timestamp);

    public int updateUserStatus(@Param("id") int id, @Param("status") String status);

    public int insertUser(User user);

    public int deleteUserById(@Param("id") int id);

    public int deleteUserByUserName(@Param("userName") String userName);

    public int deleteUserByNickName(@Param("nickName") String nickName);

    public int deleteAllUsers();

}
