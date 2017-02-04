package com.hqjl.crm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hqjl.crm.model.Auth;

public interface AuthMapper {

    public List<Auth> listAuths();

    public List<Auth> listAuthsByApplyId(@Param("applyId") String applyId);

    public List<Auth> listAuthsByApplyIdAndAuthRole(@Param("applyId") String applyId,
            @Param("authRole") String authRole);

    public List<Auth> listAuthsByMap(Map<String, Object> map);

    public long countAuthsByMap(Map<String, Object> map);

    public Auth getAuth(@Param("id") String id);

    public Auth getAuthByInfo(@Param("applyId") String applyId, @Param("userName") String userName,
            @Param("authRole") String authRole);

    public Auth getAuthByBusinessType(@Param("applyId") String applyId, @Param("businessType") String businessType,
            @Param("authRole") String authRole);

    public Auth getAuthByUserName(@Param("applyId") String applyId, @Param("userName") String userName);

    public int updateAuth(Auth auth);

    public int insertAuth(Auth auth);

    public int deleteAuth(@Param("id") String id);

    public int deleteAuthByApplyId(@Param("applyId") String applyId);

    public int deleteAuthByInfo(@Param("applyId") String applyId, @Param("userName") String userName,
            @Param("authRole") String authRole);

    public int deleteAllAuths();

}
