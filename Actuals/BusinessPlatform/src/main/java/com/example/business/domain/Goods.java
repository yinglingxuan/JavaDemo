package com.example.business.domain;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class Goods {
    private String foodId;  //商品id
    private String shopUuid;  //店家id
    private Integer typeId;  //商品类型id
    private String foodName;  //商品名称
    private String description; // 商品描述
    private BigDecimal foodPrice; //商品的价格
    private BigDecimal foodNewPrice; //商品优惠的价格
    private Integer  foodStock;    // 商品的库存
    private String foodIcon; //商品图片
}
