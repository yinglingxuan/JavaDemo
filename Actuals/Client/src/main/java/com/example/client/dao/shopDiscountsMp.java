package com.example.client.dao;

import com.example.client.domain.Discounts;
import com.example.client.domain.UserCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface shopDiscountsMp {
    //获取店家的优惠信息
    public List<Discounts> getDiscounts(@Param("shopUuid") String shopUuid);

    //获取到店家的红包
    public void addDiscounts (@Param("openid") String openid,@Param("couponid") String couponid);

    //获取店家所有红包-----
    public UserCoupon exists(@Param("openid") String openid,@Param("couponid") String couponid);


    //清除已经使用使用过的了的红包
    public void deleteDiscounts(@Param("uuid") String uuid);

}
