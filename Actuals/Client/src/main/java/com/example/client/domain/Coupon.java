package com.example.client.domain;

import lombok.Data;

import java.util.List;

@Data
public class Coupon {//平台优惠卷
    private String id;//id
    private Integer price;//价格
    private Integer integral;//积分
    private Integer full;//满减
    private String shopUuid;
}
