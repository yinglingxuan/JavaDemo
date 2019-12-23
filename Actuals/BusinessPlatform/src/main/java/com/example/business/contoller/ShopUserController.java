package com.example.business.contoller;


import com.example.business.dao.ShopUserMapper;
import com.example.business.domain.ShopUser;
import com.example.business.responseFile.BizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class ShopUserController {
    @Autowired
    ShopUserMapper shopUserMapper;


    @GetMapping("/login/test")
    public BizResponse test(HttpServletRequest request, HttpSession session){
        session.setAttribute("data","刘伟楠");
        System.out.println(request.getSession().getAttribute("data"));
        System.out.println(session.getAttribute("url"));
        System.out.println(session.getAttribute("data"));
        return BizResponse.of(request.getSession().getAttribute("data"));
    }

    @PostMapping("/shopRegister")
    public BizResponse register(@RequestBody ShopUser shopUser){
        System.out.println(shopUser);
        String entrys = shopUserMapper.shopEntryss(shopUser); //判断用户是否存在
        if(entrys!=null){
            return BizResponse.of("0200","");
        }
        String uuid= BizResponse.getUUID32();
        shopUser.setShopUuid(uuid);
        boolean register = shopUserMapper.shopUserAdd(shopUser);
        shopUserMapper.shopAdd(uuid,shopUser.getShopName());
        return BizResponse.of("注册成功");
    }
    @PostMapping("/shopEntry")
    public BizResponse Shopenery(@RequestBody ShopUser shopUser){
        String  entrys = shopUserMapper.shopEntrys(shopUser); //判断用户是否存在
        if(entrys==null){
            return BizResponse.of("0404","");
        }
        return BizResponse.of(entrys);
    }

    @GetMapping("/getShopUser")
    public BizResponse getShopUser(String uuid){
        ShopUser shopUser = shopUserMapper.getShopUser(uuid);

        return BizResponse.of(shopUser);
    }


    @PostMapping("/updateShopUser")
    public BizResponse updateShopUser(@RequestBody ShopUser shopUser){
        ShopUser shopUsers = shopUserMapper.getShopUser(shopUser.getShopUuid());
        if (shopUsers.getShopUuid().equals(shopUser.getShopUuid())&&shopUsers.getShopPassword().equals(shopUser.getShopPassword())){
            shopUserMapper.updateShopUser(shopUser.getShopUuid(),shopUser.getNewPassword());
            return BizResponse.success();
        }else{
            return BizResponse.fail("3000");
        }
    }


}
