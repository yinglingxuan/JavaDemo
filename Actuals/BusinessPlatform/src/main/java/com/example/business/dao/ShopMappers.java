package com.example.business.dao;


import com.example.business.domain.GoodsType;

import com.example.business.domain.Receive;
import com.example.business.responseFile.BizPage;
import com.example.business.responseFile.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMappers {
   //商家后台处理
    //根据店家id获取到对应的菜系列表总数
    public Long getShopCountType(@Param("uuid") String uuid);


    //获取到当前商家的类型
    public List<GoodsType> getShopType(@Param("receive") Receive receive,@Param("bizPage") Page bizPage);


    //删除当前的类型
    public void deleteShop(@Param("ids") List<Integer> ids);

    //删除当前的类型下的所有商品
    public void deleteShopGoods(@Param("ids") List<Integer> ids);

}
