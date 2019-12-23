package com.example.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class ShopData {
    private String shopId; //商家id
    private String shopStyleId;  //商家类型id
    private String matchStyle;   //配送类型
    private String shopName;   //商家名称
    private String shopStatus;  // 商家的状态（0/1）上下架
    private String shopIcon; //商家头像
    private String description; // 商家描述
    private Integer deliveryTime; // 平均配送时间---分钟
    private BigDecimal minPrice; //起送价
    private String bulletin;   // 公告
    private Integer deliveryPrice;// 配送费
    private long sale;   // 月售
    private Object infos;  // 店家图片
    private String shopPhone;  //商家联系电话
    /*@JsonFormat(timezone = "GMT+8", pattern = "HH:mm:ss")*/
    private String sellTime;   //营业开始时间
    /*@JsonFormat(timezone = "GMT+8", pattern = "HH:mm:ss")*/
    private String finishTime;   //营业结束时间
    // 店家创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;



}


