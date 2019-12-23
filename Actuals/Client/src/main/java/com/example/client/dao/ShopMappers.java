package com.example.client.dao;

import com.example.client.domain.GoodsType;
import com.example.client.domain.MapRange;
import com.example.client.domain.Receive;
import com.example.client.domain.ShopData;
import com.example.client.responseFile.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMappers {

//通过搜索内容匹配到商家信息数据
    public List<ShopData> searchShop(@Param("text") String text, @Param("map") MapRange map, @Param("page") Page page);
    public long searchShopCount(@Param("text") String text, @Param("map") MapRange map);
//通过搜索商品名称返回商家列表
    public List<ShopData> searchFood(@Param("text") String text, @Param("map") MapRange map, @Param("page") Page page);
    public long searchFoodCount(@Param("text") String text, @Param("map") MapRange map);


    //获取到商家信息数据
    public ShopData  getShop(@Param("uuid") String uuid);

    //根据店家id获取到对应的菜系列表
    public List<GoodsType> getShopList(@Param("uuid") String uuid);


    /*用户的收藏*/
    public void addLike(@Param("uuid") String uuid, @Param("openid") String openid);


    //用户是否收藏当前店铺
    public List<Receive> getLike(@Param("uuid") String uuid, @Param("openid") String openid);

    //用户取消收藏
    public void deleteLike(@Param("uuid") String uuid, @Param("openid") String openid);



   /* ///商家后台处理
    //根据店家id获取到对应的菜系列表总数
    public Long getShopCountType(@Param("uuid") String uuid);


    //获取到当前商家的类型
    public List<GoodsType> getShopType(@Param("uuid") String uuid);*/
}
       
	   
	   
	   
	   
	   
	   