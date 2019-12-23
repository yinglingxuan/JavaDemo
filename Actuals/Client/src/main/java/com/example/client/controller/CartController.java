package com.example.client.controller;


import com.example.client.dao.CartMapper;
import com.example.client.domain.Cart;
import com.example.client.responseFile.BizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class CartController {
    @Autowired
    CartMapper cartMapper;

    @PostMapping("/addCart")
    //添加购物车
    //addShopping?shop_uuid&user_openId&shop_id=1&shop_count=1
    public BizResponse cartAddPost(@RequestBody Cart cart){
        System.out.println(cart.toString());
        Integer integer = cartMapper.queryCart(cart);
        if(integer!=null){
            cartMapper.updateCart(cart);
            Integer i = cartMapper.queryCart(cart);
            if(i<=0){
                cartMapper.deleteFood(cart);
            }
        }else{
            cart.setCartUuid(BizResponse.getUUID32());
            boolean b = cartMapper.cartAdd(cart);
        }
        List<Cart> query = cartMapper.query(cart.getShopUuid(),cart.getUserOpenid());
        return BizResponse.of(query);
    }

    //获取到购物车信息
    @GetMapping("/getCart")
    public BizResponse selectCart(String shopUuid,String userOpenid){
        if(shopUuid==null || userOpenid==null){
            return BizResponse.of("400","");
        }
        List<Cart> query = cartMapper.query(shopUuid,userOpenid);
        return BizResponse.of(query);
    }

    //清空购物车
    @GetMapping("/getDeleteCart")
    public BizResponse delete(String userOpenid,String shopUuid){
        boolean delete = cartMapper.delete(shopUuid, userOpenid);
        return BizResponse.of(delete);
    }
}
