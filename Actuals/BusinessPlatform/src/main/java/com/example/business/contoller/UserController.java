package com.example.business.contoller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.business.config.WXApi;
import com.example.business.dao.ShopCouponMapper;
import com.example.business.dao.UserInfoMapper;
import com.example.business.domain.DiscountCoupon;
import com.example.business.domain.UserInfo;
import com.example.business.entity.BizPage;
import com.example.business.entity.BizResponse;
import com.example.business.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    ShopCouponMapper ShopCouponMapper;

    @Autowired
    WXApi wxApi;


    //获取到当前店买过的所有用户
    @GetMapping("/getUser")
    public BizResponse getUser(String shopId,BizPage bizPage){
        System.out.println(bizPage.toString());
        List<UserInfo> user = userInfoMapper.getUser(shopId, Page.of(bizPage.getNo(),bizPage.getSize()));
        bizPage.setTotal(userInfoMapper.getUserCount(shopId));
        return BizResponse.of(user,bizPage);
    }

    //给选中的用户发送一个红包
    @PostMapping("/discountCoupon")
    public BizResponse discountCoupon(@RequestBody DiscountCoupon discountCoupon){
        boolean b = ShopCouponMapper.addUserCoupon(discountCoupon);
        String string = wxApi.get(String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s","wx7f01e1d9f26ff4dd", "5c6e8978d75735af3a306e223ec981ac"));
        JSONObject json = JSON.parseObject(string);
        String access_token = json.getString("access_token");

        String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        //通知模板
        String data="{\n" +
                "   \"touser\":\"o2B1f1XNBQT0IyhHV-QEdKXsWmKk\",\n" +
                "   \"template_id\":\"tpDkdyW6ovxfCaNL8cFBIlAcOXIsnP7o1GwlPWMxExM\",\n" +
                "   \"url\":\"http://weixin.qq.com/download\",          \n" +
                "   \"data\":{\n" +
                "           \"first\": {\n" +
                "               \"value\":\"饿了么\",\n" +
                "               \"color\":\"#173177\"\n" +
                "           },\n" +
                "           \"keyword1\":{\n" +
                "               \"value\":\"小东店铺\",\n" +
                "               \"color\":\"#173177\"\n" +
                "           },\n" +
                "           \"keyword2\": {\n" +
                "               \"value\":\"2019-2-2\",\n" +
                "               \"color\":\"#173177\"\n" +
                "           },\n" +
                "           \"keyword3\": {\n" +
                "               \"value\":\"小东店铺送了您一张优惠卷\",\n" +
                "               \"color\":\"#173177\"\n" +
                "           },\n" +
                "           \"remark\":{\n" +
                "               \"value\":\"欢迎再次购买！\",\n" +
                "               \"color\":\"#173177\"\n" +
                "           }\n" +
                "   }\n" +
                "}";
        String s = sendPost(url, data);
        System.out.println(discountCoupon);
        return BizResponse.of(b);
    }


    //发送post 请求
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");//这个*/* 是支持所有类型
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
