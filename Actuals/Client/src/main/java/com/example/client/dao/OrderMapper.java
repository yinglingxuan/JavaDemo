package com.example.client.dao;

import com.example.client.domain.Cart;
import com.example.client.domain.CartGoods;
import com.example.client.domain.UserOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    //录入订单
    public boolean addOrder(@Param("id") String id, @Param("userOpenid") String userOpenid);
    //录入订单详情
    public boolean addOrderDetail(@Param("orderId") String orderId, @Param("list") List<Cart> list);
    //查询订单
    public UserOrder userOrderQuery(@Param("orderId") String orderId);
    //删除预订单
    public boolean orderDelete(@Param("orderId") String orderId);
    //删除详情订单
    public boolean orderDeleteAll(@Param("orderId") String orderId);
    //查询出预订单单号
    public String orderQuery(@Param("openId") String openId);
    //根据订单号查询用户的所有的订单详情
    public List<CartGoods> userOrderQuerys(@Param("orderId") String orderId);
    //修改订单状态
    public boolean updateOrder(@Param("state") String state, @Param("remark") String remark, @Param("addressId") String addressId, @Param("orderId") String orderId);
    //支付成功后的订单详情
    public UserOrder userOrderAddessQuery(@Param("orderId") String orderId);
    //查询出用户的历史订单
    public List<UserOrder> userOrderAddessQueryAll(@Param("userOpenid") String userOpenid);
    //根据订单号修改修改uuid
    public boolean userOrderUpdate(@Param("uuid")String uuid,@Param("orderId")String orderId);
    //根据订单uuid 修改 订单状态
    public boolean updateOrders(@Param("uuid")String uuid);
    //根据uuid 获取到 opneid 和 当前商家id
    public Cart queryOrders(@Param("uuid")String uuid);
    //根据订单号获取到 opneid
    public String orderUserOpenid(@Param("orderId")String orderId);


}
