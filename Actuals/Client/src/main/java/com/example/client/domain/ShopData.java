package com.example.client.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class ShopData {
    @JsonProperty("shopUuid")
    private String shopId; //商家id
    private Integer shopStyleId;  //商家类型id
    private String matchStyle;   //配送类型
    private String shopName;   //商家名称
    private String shopStatus;  // 商家的状态（0/1）上下架
    private String shopIcon; //商家头像

    private String description; // 商家描述
    private Integer deliveryTime; // 平均配送时间---分钟
    private BigDecimal minPrice; //起送价
    private Integer deliveryPrice;  //配送费
    private String bulletin;   // 公告
    private long sale;   // 月售
    private Object infos;  // 店家图片
    private String shopPhone;  //商家联系电话
    private double shopLatitude;//纬度
    private double shopLongitude;//经度
    @JsonFormat(timezone = "GMT+8", pattern = "MM-dd-HH")
    private Date sellTime;   //营业开始时间
    @JsonFormat(timezone = "GMT+8", pattern = "MM-dd-HH")
    private Date finishTime;   //营业结束时间
    // 店家创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd-HH:mm")
    private Date createTime;

    private double distince;//距离 /km
}
