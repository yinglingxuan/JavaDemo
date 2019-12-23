package com.example.business.dao;


import com.example.business.domain.ShopUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ShopUserMapper {
    public boolean shopUserAdd(@Param("shopUser") ShopUser shopUser);//录入商家用户
    public String shopEntrys(@Param("shopUser") ShopUser shopUser);//登录校验
    public String shopEntryss(@Param("shopUser") ShopUser shopUser);//查询用户名存不存在
    public boolean shopAdd(@Param("shopUuid") String shopUuid, @Param("shopName") String shopName);//录入商店


    //获取当前的用户的信息
    public ShopUser getShopUser(@Param("uuid")String uuid);
    //修改密码
    public void updateShopUser(@Param("uuid")String uuid,@Param("newPossword")String newPossword);

}
