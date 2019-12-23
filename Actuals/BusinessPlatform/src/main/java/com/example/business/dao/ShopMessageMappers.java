package com.example.business.dao;


import com.example.business.domain.GoodsType;
import com.example.business.domain.Receive;
import com.example.business.domain.ShopData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMessageMappers {
   //商家后台信息处理
    //根据店家id获取到对应的菜系列表总数
    public ShopData getShopMessage(@Param("uuid") String uuid);


    //修改到当前商家的类型
    public void updateShop(@Param("shop") ShopData shop);


    /*//删除当前的类型
    public void deleteShop(@Param("ids") List<Integer> ids);

    //删除当前的类型下的所有商品
    public void deleteShopGoods(@Param("ids") List<Integer> ids);*/

}
