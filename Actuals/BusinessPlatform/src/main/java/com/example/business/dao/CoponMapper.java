package com.example.business.dao;

import com.example.business.domain.Copons;
import com.example.business.entity.BizPage;
import com.example.business.vo.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoponMapper {
    //获取到优惠券的信息
    public List<Copons> getCop(@Param("cop") Copons copons,@Param("biz") Page page);
    public Long count(@Param("cop") Copons copons);
    //添加优惠卷
    public void addCop(Copons copons);
    //修改优惠券
    public void upDataCop(Copons copons);
    //删除优惠券
    public void deleCop(@Param("ids")List<String> ids);
}
