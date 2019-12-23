package com.example.client.controller;

import com.example.client.dao.shopDiscountsMp;
import com.example.client.domain.Discounts;
import com.example.client.domain.UserCoupon;
import com.example.client.responseFile.BizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ShopDiscountsController {
    @Autowired
    public shopDiscountsMp shopDiscounts;

    @GetMapping("/getDiscounts")
    public BizResponse getShop(String shopUuid){
        //获取到店家优惠信息
        List<Discounts> discounts = shopDiscounts.getDiscounts(shopUuid);
        return BizResponse.of(discounts);
    }

    //点击领取店家红包api
    @PostMapping("/addDiscounts")
    public BizResponse addDiscounts(@RequestBody Map<String,String > map){
        String openid = map.get("openid");
        String couponid = map.get("couponid");
        //判断用户是否已经领取过了，领取过了就不能再领取了
        //获取用户所有的红包信息，判断是否已经领取过了
        UserCoupon exists = shopDiscounts.exists(openid, couponid);
        /*System.out.println(exists);*/
        if (exists==null || exists.equals("")){
            //领取店家红包
            shopDiscounts.addDiscounts(openid,couponid);
            return BizResponse.of("领取成功");
        }else{
            return BizResponse.fail("4003");
        }
    }

    //选择消费成功后，清除消费了的红包
    @PostMapping("/deleteDiscounts")
    public BizResponse deleteDiscounts(@RequestBody String uuid){
        System.out.println(uuid);
       shopDiscounts.deleteDiscounts(uuid);
        return BizResponse.of("OK");
    }
}
