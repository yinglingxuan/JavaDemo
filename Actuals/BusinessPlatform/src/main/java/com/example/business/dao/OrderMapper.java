package com.example.business.dao;

import com.example.business.entity.Order;
import com.example.business.vo.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> queryOrder(@Param("shopUuid") String shopUuid, @Param("page") Page page);
    Long queryOrderCount(@Param("shopUuid") String shopUuid);
}
