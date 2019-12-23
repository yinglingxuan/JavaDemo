package com.example.client.domain;

import lombok.Data;

@Data
public class Cart {
    private String cartUuid;//购物车id
    private String userOpenid;//微信openid
    private String shopUuid;//店铺uuid
    private String foodUuid;//商品uuid
    private String foodCount;//数量
}
