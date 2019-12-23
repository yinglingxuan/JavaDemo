package com.example.business.dao;

import com.example.business.entity.FoodType;
import com.example.business.vo.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoodTypeMapper{
//  查询商品所有类型信息
List<FoodType> searchAllType(@Param("shopUuid") String shopUuid, @Param("page") Page page);
    long searchCount(@Param("shopUuid") String shopUuid);

// 商品类型的添加
int add(@Param("shopUuid") String shopUuid, @Param("type") String type);
// 商品类型的修改
int update(@Param("shopUuid") String shopUuid, @Param("id") Integer id, @Param("newType") String newType);

//  商品类型的删除
int delete(@Param("ids") List<Integer> ids);
    int deleteFood(@Param("ids") List<Integer> ids);


}
