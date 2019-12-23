package com.example.client.controller;

import com.example.client.dao.CouponMapper;
import com.example.client.dao.MemberMapper;
import com.example.client.domain.Member;
import com.example.client.responseFile.BizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@RestController
public class MemberController {

    @Autowired
    MemberMapper memberMapper;
    @Autowired
    CouponMapper couponMapper;
    //注册会员调用支付
    @PostMapping("/member")
    public BizResponse getMember(@RequestBody Member member, HttpServletRequest req) throws IOException, NoSuchAlgorithmException, ParseException {
        member.setUserOpenid("os6oy1oSt3qvUazTgfhKtCuyrUuA");
        //查询出uuid 然后删除重新创建一个新的
        String querys = memberMapper.querys(member.getUserOpenid());
        if(querys!=null){
            memberMapper.delete(querys);
        }
        double price=member.getMonth()*15;//每个月15元

        String uuid=BizResponse.getUUID32();
        String url="https://wx.panqingshan.cn/linzhisong/menber/success";
        Map<String, Object> result = BizResponse.payment(price,uuid,member.getUserOpenid(), req,url);//获取到预支付数据
        String date = date(member.getMonth(),member.getUserOpenid());//获取到过期时间
        boolean insert = memberMapper.insert(uuid,member.getUserOpenid(),date);
        return  BizResponse.of(result);
    }

    //支付成功后调用
    @PostMapping("/menber/success")
    public BizResponse getSuccess(@RequestBody String xml) throws ParseException {
//        XmlHelper xmlHelper = XmlHelper.of(xml);
//        Map<String, String> prepareResults = xmlHelper.toMap();
//        System.out.println(prepareResults.toString());
//        System.out.println(prepareResults.get("out_trade_no"));
        String uuid=xml;
        System.out.println(uuid);

        Member member = memberMapper.query(uuid);
        System.out.println(uuid+"  "+member.toString());
        boolean update = memberMapper.update(member);
        memberMapper.delete(uuid);//支付成功 删除掉会员凭证
        String uuid32 = BizResponse.getUUID32();
        //注册会员成功后获取红包
        couponMapper.addUserCoupon(uuid32,member.getUserOpenid(),"fd9a6dc7-080e-11e9-a06a-0a002700000d");

        return  BizResponse.of(member);
    }

    //这一步主要是用来查询出用户有没有充值过会员 有就获取到他的过期时间然后在计算出过期的时间是否小于当前时间如果小于就将
    //时间改为今天在加上充值的时间，如果大于就在这个基础之上在加上充值的时间
    public String date(Integer amount,String userOpenid) throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String dataTime = memberMapper.queryTime(userOpenid);//获取到用户的会员时间
        Calendar calendar = Calendar.getInstance();
        if(dataTime!=null){
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataTime));
            calendar.getTimeInMillis();
            if(calendar.getTimeInMillis()>date.getTime()){
                date=new Date(calendar.getTimeInMillis());
            }
        }
        Date dt = sdf.parse(String.valueOf(sdf.format(date.getTime())));

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.MONTH, amount);
        return sdf.format(rightNow.getTime());
    }

}
