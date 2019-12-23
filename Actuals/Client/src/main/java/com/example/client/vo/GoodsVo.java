package com.example.client.vo;

import com.example.client.domain.ShopData;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GoodsVo implements Serializable{
    //店家uuid
    private String shopUuid;
    //商品名称
    private String foodUuid;
    //商品名称
    private String foodName;
    //商家对象
    private List<ShopData> shopData;
}
