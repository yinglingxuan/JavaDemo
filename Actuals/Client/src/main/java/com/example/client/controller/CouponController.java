package com.example.client.controller;

import com.example.client.dao.CouponMapper;
import com.example.client.domain.Coupon;
import com.example.client.domain.UserCoupon;
import com.example.client.responseFile.BizResponse;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CouponController {

    @Autowired
    CouponMapper couponMapper;

    @GetMapping("/getCouponAll")
    public BizResponse getCoupon(){
        List<Coupon> query = couponMapper.query();
        return BizResponse.of(query);
    }

    @GetMapping("/getUserCoupon")
    public BizResponse getUserCoupon(String openid){
        List<UserCoupon> userCoupons = couponMapper.queryCoupon(openid);
        return BizResponse.of(userCoupons);
    }

    @GetMapping("/queryShopCoupon")
    public BizResponse queryShopCoupon(String shopId){
        List<Coupon> coupons = couponMapper.queryShopCoupon(shopId);
        return  BizResponse.of(coupons);
    }
}
