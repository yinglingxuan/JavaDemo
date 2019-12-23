package com.example.client.dao;

import com.example.client.domain.Favourable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FavourableMapper {
    //获取优惠券
    public Favourable query(@Param("openid")String openid);
    //删除优惠卷
    public boolean delete(@Param("id")String id);
    //加入优惠券
    public boolean add(@Param("favourable") Favourable favourable);
}