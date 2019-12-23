package com.example.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class GoodsType {
    private Integer id;    /*商品类型的id*/
    private Integer shopUuid;    /*店家的id*/
    private String typeName;  /*商品类型的名字*/
    private long typeCount;  /*这个类型的下单数量*/

    //    @JSONField(name="foods")
    @JsonProperty("foods")      //*商品类型中的商品列表  指定别名*//*
    private List<Goods> goods;   //商品列表

}
