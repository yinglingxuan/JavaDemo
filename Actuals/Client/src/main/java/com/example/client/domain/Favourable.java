package com.example.client.domain;

import lombok.Data;

@Data
public class Favourable{
    private String id;//优惠券id
    private String userOpenid;//用户的openid
    private double sum;//金额
}