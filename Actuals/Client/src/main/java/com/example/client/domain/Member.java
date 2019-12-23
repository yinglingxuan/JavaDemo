package com.example.client.domain;

import lombok.Data;

@Data
public class Member{
    private String userOpenid; //用户的openid
    private Integer month; //需要几个月
    private String uuid;  //会员支付的唯一标示
    private String date;
}
