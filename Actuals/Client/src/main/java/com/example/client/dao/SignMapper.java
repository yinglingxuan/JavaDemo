package com.example.client.dao;

import com.example.client.domain.Coupon;
import com.example.client.domain.Sign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SignMapper {

    //返回当前签到的天数
    public Sign getSign(@Param("openid") String openid);

    //如果第一次签到就添加到表中
    public void addSign(@Param("openid") String openid);

    //判断当前签到是否过了一天
    public Integer space(@Param("date") String date);

    //修改积分和签到天数
    public void updateSign(@Param("openid") String openid,@Param("sky") Integer sky,@Param("integral") Integer integral);

    //获取到当前的红包对应的积分
    public Coupon getCoupon (@Param("couponid") String couponid);


    //满足兑换条件后的红包添加
    public void addCoupon (@Param("openid") String openid,@Param("couponid") String couponid);
}
