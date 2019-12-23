package com.example.client.domain;

import lombok.Data;

import java.util.List;

@Data
public class UserCoupon {//用户优惠卷
    private String uuid;
    private String userOpenid;
    private String couponId;
    private String couponTime;
    private Coupon coupon;
}
