package com.example.business.domain;

import lombok.Data;

import java.util.List;

@Data
public class UserOrder{
    private String id;//订单号
    private String userOpenid;//用户openid
    private String remark;//备注
    private String orderState;//状态
    private String shopName;//商点名
    private String shopIcon;//图片
    private List<CartGoods> foods;
    private Addesss addess;
}
