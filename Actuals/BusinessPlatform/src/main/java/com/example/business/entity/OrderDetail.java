package com.example.business.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDetail {
    private String id;//id
    private String orderId;//订单号
    private String shopUuid;//店铺uuid
    private String foodUuid;//商品uuid
    private Integer foodCount;//商品数量
    private Date createTime;
    private Date updateTime;

//商品对象
    private List<Food> food;
}
