package com.example.business.contoller;

import com.example.business.dao.CoponMapper;
import com.example.business.dao.ShopCouponMapper;
import com.example.business.domain.Copons;
import com.example.business.domain.ShopCoupon;
import com.example.business.entity.BizPage;
import com.example.business.entity.BizResponse;
import com.example.business.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoponController {
    @Autowired
    public CoponMapper coponMapper;

    //获取优惠券
    @GetMapping("/getCop")
    public BizResponse getCop(Copons coupons,BizPage bizPage){
        List<Copons> cop = coponMapper.getCop(coupons, Page.of(bizPage.getNo(), bizPage.getSize()));
        bizPage.setTotal(coponMapper.count(coupons));
        return BizResponse.of(cop,bizPage);
    }

    //添加优惠券
    @PostMapping("/addCop")
    public BizResponse addCop(@RequestBody Copons coupons){
        /*System.out.println(coupons);*/
        coponMapper.addCop(coupons);
        return BizResponse.success();
    }

    //修改优惠券信息
    @PostMapping("/upDataCop")
    public BizResponse upDataCop(@RequestBody Copons coupons){
        /*System.out.println(coupons);*/
        coponMapper.upDataCop(coupons);
        return BizResponse.success();
    }

    //删除优惠券
    @PostMapping("/deleCop")
    public BizResponse deleCop(@RequestBody List<String> ids){
        System.out.println(ids);
        coponMapper.deleCop(ids);
        return BizResponse.success();
    }
}
