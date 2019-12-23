package com.example.business.dao;

import com.example.business.domain.DiscountCoupon;
import com.example.business.domain.ShopCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;
@Mapper
public interface ShopCouponMapper {
    //商家优惠卷插入
    public boolean inser(@Param("coupon")ShopCoupon shopCoupon);
    //商家优惠卷删除
    public boolean delete(@Param("id")String id);
    //根据商家id查询出该商家的所有优惠卷
    public List<ShopCoupon> query(@Param("shopId")String shopId);
    //给选中的用户发送一个红包
    public boolean addUserCoupon(DiscountCoupon coupon);
}
