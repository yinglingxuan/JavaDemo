package com.example.business.dao;

import com.example.business.domain.DiscountCoupon;
import com.example.business.domain.UserInfo;
import com.example.business.vo.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    //根据商店id查询出所有买过的用户
    public List<UserInfo> getUser(@Param("shopId")String shopId, @Param("page")Page page);
    //根据商店id拿到所有买过的用户的总数
    public Long getUserCount(@Param("shopId")String shopId);


}
