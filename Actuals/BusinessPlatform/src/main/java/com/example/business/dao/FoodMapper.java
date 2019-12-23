package com.example.business.dao;

import com.example.business.entity.Food;
import com.example.business.vo.FoodVo;
import com.example.business.vo.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoodMapper {
//  商品的增加
int add(@Param("food") Food food);
//商品的删除
int delete(@Param("ids") List<String> ids);
//商品的修改
int update(@Param("food") Food food);
//查询商家所有商品
List<Food> search(@Param("food") Food food, @Param("page") Page page);
    Long searchCount(@Param("shopUuid") String shopUuid);
}
