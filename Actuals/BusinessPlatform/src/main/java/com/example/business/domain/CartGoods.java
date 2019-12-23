package com.example.business.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartGoods {
    private String id;//商品id
    private String type;//商品类型
    private String name;//商品名称
    private String description;//描述
    private double price;//优惠价格
    private double oldPrice;//原价格
    private String info;//优惠信息
    private String icon;//商品图片
    private Integer count;//数量
}
