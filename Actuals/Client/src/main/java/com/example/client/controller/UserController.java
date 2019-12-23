package com.example.client.controller;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.client.config.WXApi;
import com.example.client.dao.UserInfosMappers;
import com.example.client.domain.ShopData;
import com.example.client.domain.UserInfos;
import com.example.client.responseFile.BizPage;
import com.example.client.responseFile.BizResponse;
import com.example.client.responseFile.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/come/user")
public class UserController {
    @Autowired
    private UserInfosMappers userInfosMappers;

    @Autowired
    private WXApi wxApi;
//  获取试验的
    @GetMapping("/getInfos")
    @ResponseBody
    public BizResponse getInfos(@RequestParam("openid")String openid){
        UserInfos userInfos = userInfosMappers.queryInfos(openid);
        return BizResponse.of(userInfos);
    }
    @GetMapping("/login/test")
    @ResponseBody
    public BizResponse test(HttpServletRequest request){
        request.getSession().setAttribute("url", request.getRequestURL());
        return BizResponse.of(request.getSession().getAttribute("url"));
    }
    @GetMapping("/login")

    public String login(String code, String state, HttpSession session) throws IOException {
        // code -> at -> userinfo
        JSONObject userInfoJSON = JSON.parseObject(wxApi.getUserInfoWithCode(code));  //通过code拼接连接获取这个网页的数据获取到code
        UserInfos adjust = isExistsOpenid(userInfoJSON);
        List<UserInfos> userInfosList=userInfosMappers.query();
        int mark=-1;
        for (UserInfos user:userInfosList) {
            if (user.getUserOpenid().equals(userInfoJSON.getString("openid"))){
                mark=1;
                System.out.println(user);
                break;
            }
        }
        if (userInfoJSON.getString("openid")!=null){
            if(mark==-1){
                userInfosMappers.add(adjust);
            }else{
               userInfosMappers.update(adjust);
            }
        }
        // 保持登录
//session.setAttribute("USER", userInfoJSON);   //将用户信息保存到session里面
        System.err.println("userInfoJSON::"+userInfoJSON);
        System.err.println(userInfoJSON.getString("openid")+"\t存3取用户session"+session.getAttribute("USER"));
        return "redirect:/#/home?openid="+userInfoJSON.getString("openid");

    }
    public static UserInfos isExistsOpenid(JSONObject userInfoJSON){
        UserInfos userInfos2=new UserInfos();
        userInfos2.setUserOpenid(userInfoJSON.getString("openid"));
        userInfos2.setUserCity(userInfoJSON.getString("city"));
        userInfos2.setUserCountry(userInfoJSON.getString("country"));
        userInfos2.setUserHeads(userInfoJSON.getString("headimgurl"));
        userInfos2.setUserLanguage(userInfoJSON.getString("language"));
        userInfos2.setUserName(userInfoJSON.getString("nickname"));
        userInfos2.setUserProvince(userInfoJSON.getString("province"));
        userInfos2.setUserSex(userInfoJSON.getString("sex"));
        System.err.println(userInfos2);
        return  userInfos2;
    }



}
