package com.example.business.domain;

import lombok.Data;

@Data
public class Copons {
    private String id; //优惠券的id
    private Integer price ; //优惠的价格
    private Integer integral; //对应的积分
    private Integer full;   //条件满多少可以用
    private String shopUuid;  //商家id
}
