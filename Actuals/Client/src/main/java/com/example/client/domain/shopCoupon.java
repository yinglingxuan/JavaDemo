package com.example.client.domain;

import lombok.Data;

@Data
public class shopCoupon {//商家优惠卷
    private String id;
    private String shopUuid;
    private Integer price;
    private Integer full;
}
