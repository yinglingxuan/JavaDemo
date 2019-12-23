package com.example.client.dao;

import com.example.client.domain.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {
    public boolean cartAdd(@Param("cart") Cart cart);
    //获取该用后的购物车
    public List<Cart> query(@Param("shopUuid") String shopUuid, @Param("userOpenid") String userOpenid);
    //删除该用户的购物车
    public boolean delete(@Param("shopUuid") String shopUuid, @Param("userOpenid") String userOpenid);
    //查询该用户是否加给这个商品
    public Integer queryCart(@Param("cart") Cart cart);
    //修改购物车的商品数量
    public boolean updateCart(@Param("cart") Cart cart);
    //修改购物车的商品数量
    public boolean deleteFood(@Param("cart") Cart cart);
}
