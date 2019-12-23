package com.example.client.dao;

import com.example.client.domain.Addesss;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressMapper {
    //查询用户所有地址
    public List<Addesss> query(@Param("openId") String openId);
    //修改地址
    public boolean update(@Param("addesss") Addesss addesss);
    //录入地址
    public boolean add(@Param("addesss") Addesss addesss);
    //删除地址
    public boolean delete(@Param("id") String id);
    //查询地址
    public Addesss idAddress(@Param("addressId") String addressId);
    //添加订单地址
    public boolean orderAddAddress(@Param("addesss") Addesss addesss);
    //根据订单号获取查看是否有地址id
    public String orderAddressId(@Param("orderId")String orderId);
    //根据地址id 删除地址
    public boolean deleteAddress(@Param("addressId")String addressId);

}
