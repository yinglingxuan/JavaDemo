package com.example.business.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Food {
    private String foodUuid;//商品Uuid
    private String shopUuid;//商家Uuid
    private Integer typeId;//商品类型
    private String foodName;//商品名称
    private String description;//商品描述
    private BigDecimal foodPrice;//商品老的价格
    private BigDecimal foodNewPrice;//商品新价格
    private Integer foodStock;//商品库存
    private String foodIcon;//商品图片
    private Date createTime;
    private Date updateTime;
//   private Integer id;//商品类型id

    private String typeName;//类型名称
    private Integer typeCount;//类型销售量


}
