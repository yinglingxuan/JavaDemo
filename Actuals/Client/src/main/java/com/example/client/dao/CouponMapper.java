package com.example.client.dao;

import com.example.client.domain.Coupon;
import com.example.client.domain.UserCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CouponMapper {
    //获取到所有优惠卷
    public List<Coupon> query();
    //给用户增加优惠卷
    public boolean addUserCoupon(@Param("uuid")String uuid,@Param("openid")String openid,@Param("couponId")String couponId);
    //获取用户所有的平台红包
    public List<UserCoupon> queryCoupon(@Param("openid")String openid);
    //根据uuid 获取到优惠卷价格
    public Integer queryPrice(@Param("uuid")String uuid);
    //根据shopid 获取到商家所有的优惠卷
    public List<Coupon> queryShopCoupon(@Param("shopId")String shopId);

}
