package com.example.client.dao;

import com.example.client.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    //修改用户为会员
    public boolean update(@Param("member") Member member);
    //给用户做个支付的唯一标识
    public boolean insert(@Param("uuid") String uuid, @Param("openid") String openid, @Param("date") String date);
    //拿到购买的会员的信息
    public Member query(@Param("uuid") String uuid);
    //拿到该用户uuid
    public String querys(@Param("openid") String openid);
    //支付成功后删除标识 通过uuid删除
    public boolean delete(@Param("uuid") String uuid);
    //获取到会员时间
    public String queryTime(@Param("openid")String openid);

}
