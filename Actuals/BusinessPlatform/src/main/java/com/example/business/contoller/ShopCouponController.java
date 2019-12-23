package com.example.business.contoller;

import com.example.business.dao.ShopCouponMapper;
import com.example.business.domain.ShopCoupon;
import com.example.business.entity.BizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShopCouponController {
    @Autowired
    ShopCouponMapper shopCouponMapper;

    @PostMapping("/addShopCoupon")
    public BizResponse addShopCoupon(@RequestBody ShopCoupon shopCoupon){
        boolean inser = shopCouponMapper.inser(shopCoupon);
        return BizResponse.of(inser);
    }

    @GetMapping("/deleteShopCoupon")
    public BizResponse deleteCoupon(String id){
        boolean delete = shopCouponMapper.delete(id);
        return  BizResponse.of(delete);
    }

    @GetMapping("/queryCoupon")
    public BizResponse query(String shopId){
        List<ShopCoupon> query =shopCouponMapper.query(shopId);
        return BizResponse.of(query);
    }
}
