package com.example.client.dao;

import com.example.client.domain.UserInfos;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfosMappers {
    public UserInfos queryInfos(String openid);
    public void add(@Param("userInfos")UserInfos userInfos);
    public List<UserInfos> query();
    public void update(@Param("user")UserInfos user);
    public void updateMap(@Param("user")UserInfos user);
}
