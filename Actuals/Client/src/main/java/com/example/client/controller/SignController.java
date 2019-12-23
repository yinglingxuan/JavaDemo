package com.example.client.controller;

import com.example.client.dao.ShopMappers;
import com.example.client.dao.SignMapper;
import com.example.client.domain.Coupon;
import com.example.client.domain.ShopData;
import com.example.client.domain.Sign;
import com.example.client.responseFile.BizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SignController {


    @Autowired
    public SignMapper signMapper;

    //用户签到获取到签到的天数
    @GetMapping("/getSign")

    public BizResponse getShop(String openid){
        //获取到当前签到的天数
        Sign sign = signMapper.getSign(openid);
        if(sign==null) {   //判断如果为空代表以前没有签到过，要添加
            signMapper.addSign(openid);
            Sign sign2 = signMapper.getSign(openid);
            return BizResponse.of(sign2);
        }else{
            //判断当前签到时间和上次签到时间是否为一天
            Integer space = signMapper.space(sign.getUpdateTime());
            //如果判断大于1天了，那么就再判断上次签到的天数是否为7 如果为7 就变为1 ，否则签到加一  积分加10
            if(space>=1){
                if(sign.getSky()==7){  //判断如果等于7就直接变为签到1  并且积分加30
                    signMapper.updateSign(openid,1,sign.getIntegral()+30);
                    // 修改成功后重新获取到最新的数据发送到前端
                    Sign sign2 = signMapper.getSign(openid);
                    return BizResponse.of(sign2);
                }else{ //如果不为7就每天加1
                    signMapper.updateSign(openid,sign.getSky()+1,sign.getIntegral()+10);
                    // 修改成功后重新获取到最新的数据发送到前端
                    Sign sign2 = signMapper.getSign(openid);
                    return BizResponse.of(sign2);
                }
            }else{
                //当前重复前端失败码
                return BizResponse.fail("4001");
            }
        }
    }

    //获取到当前签到的天数
    @GetMapping("/getSky")
    public BizResponse getSky(String openid){
        Sign sign = signMapper.getSign(openid);
        return BizResponse.of(sign);
    }


    //通过openid，和对应的红包id 兑换对应的红包
    @PostMapping("/convert")
    public BizResponse convert(@RequestBody Map<String,String > map){
        String openid = map.get("openid");
        String couponid = map.get("couponid");
        System.out.println(couponid+"------"+openid);
        //获取到当前红包对应的积分
        Coupon coupon = signMapper.getCoupon(couponid);
        //获取到当前用户对应的积分
        Sign sign = signMapper.getSign(openid);

        if(sign.getIntegral()>=coupon.getIntegral()){ //判断是否满足兑换的条件
            //满足兑换后用户减去对应的积分
            signMapper.updateSign(openid,null,sign.getIntegral()-coupon.getIntegral());
            //添加到用户红包表中
            signMapper.addCoupon(openid,couponid);
            return BizResponse.of("兑换成功");
        }else{
            return BizResponse.fail("4002");
        }
    }

}
