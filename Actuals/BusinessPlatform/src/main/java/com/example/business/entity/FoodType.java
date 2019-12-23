package com.example.business.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FoodType {
    private Integer id;//分类的id
    private String shopUuid;//商家uuid
    private String typeName;//分类名称
    private Integer typeCount;//这个类型的下单数量
    private Date createTime;//创建时间
    private Date updateTime;//最近修改的时间
}
