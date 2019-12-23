package com.example.business.dao;

import com.example.business.domain.Discounts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface DisMapper {
    //获取到优惠的信息
    public List<Discounts> selects(@Param("shopUuid") String shopUuid);

    //修改优惠的信息
    public void upDataDis(Discounts discounts);

    //删除优惠信息
    public void DeleteDis(@Param("ids") List<Integer> ids);


    //添加优惠信息
    public void addDis(Discounts discounts);
}
