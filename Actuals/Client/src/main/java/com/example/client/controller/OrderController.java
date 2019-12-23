package com.example.client.controller;

import com.alibaba.fastjson.JSON;
import com.example.client.dao.AddressMapper;
import com.example.client.dao.CartMapper;
import com.example.client.dao.CouponMapper;
import com.example.client.dao.OrderMapper;
import com.example.client.domain.*;
import com.example.client.responseFile.BizResponse;
import com.example.client.websocket.WebSocketTest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class OrderController {
    @Autowired
    AddressMapper addressMapper;

    @Autowired
    OrderMapper orderMapper;
    
    @Autowired
    CartMapper cartMapper;

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    WebSocketTest webSocketTest;
    //生成预支付订单
    //getShopping?shop_uuid&user_uuid
    @PostMapping("/addPrepay")
    public BizResponse prepayment(@RequestBody Cart cart){
        System.out.println(cart.toString());
        String s = orderMapper.orderQuery(cart.getUserOpenid());
        if(s!=null){
            orderMapper.orderDeleteAll(s);
            orderMapper.orderDelete(s);
        }
        String string=getNum19();
        String uuid=BizResponse.getUUID32();
        boolean b = orderMapper.addOrder(string,cart.getUserOpenid());//生成订单
        if(b){
            List<Cart> query = cartMapper.query(cart.getShopUuid(),cart.getUserOpenid());
            if(query.size()<=0){

            };
            boolean orderDetail = orderMapper.addOrderDetail(string, query);
        }
        UserOrder userOrder = orderMapper.userOrderQuery(string);
        return BizResponse.of(userOrder);
    }

    @GetMapping("/orderQuery")
    public BizResponse test(String userOpenid){
        List<UserOrder> userOrders = orderMapper.userOrderAddessQueryAll(userOpenid);
        return BizResponse.of(userOrders);
    }

    //调用支付
    ///surePay?orderId=订单号,地址id，备注
    @PostMapping("/surePay")
    public BizResponse orderAdd(@RequestBody Pay pay,HttpServletRequest req) throws IOException, NoSuchAlgorithmException{
        int prices=0;
        if( pay.getUuid()!=null){
            prices = couponMapper.queryPrice(pay.getUuid());
        }
        List<CartGoods> cartGoods = orderMapper.userOrderQuerys(pay.getOrderId());
        String s = addressMapper.orderAddressId(pay.getOrderId());

        if(s!=null){
            addressMapper.deleteAddress(s);
        }

        Addesss addesss = addressMapper.idAddress(pay.getAddressId());
        String uuid=BizResponse.getUUID32();
        addesss.setAddressId(uuid);
        addressMapper.orderAddAddress(addesss);

        double price=0;
        for (CartGoods cg:cartGoods) {
            price+=Integer.valueOf(cg.getCount())*cg.getPrice();
        }
        orderMapper.updateOrder(null,pay.getRemark(),uuid,pay.getOrderId());

        // 订单: 100
        // 生成预付单
        orderMapper.userOrderUpdate(uuid,pay.getOrderId());

//        String opneid = orderMapper.orderUserOpenid(pay.getOrderId());
        String url="https://wx.panqingshan.cn/linzhisong/order/success";//支付成功通知回调地址
        Map<String, Object> result = BizResponse.payment(price-prices<=0?0:price-prices,uuid,pay.getOpenId(), req,url);
        return BizResponse.of(result);
    }

    //支付成功调用此接口
    @PostMapping("/order/success")
    public String success(@RequestBody String xml){
        System.out.println(xml);
        XmlHelper xmlHelper = XmlHelper.of(xml);
        Map<String, String> prepareResults = xmlHelper.toMap();
        System.out.println(prepareResults.toString());
        System.out.println(prepareResults.get("out_trade_no"));


//        boolean b = orderMapper.updateOrder("1",null,null,orderId);
//        UserOrder userOrder = orderMapper.userOrderAddessQuery(orderId);
        Map result = new HashMap<>();
        result.put("return_code", "SUCCESS");
        result.put("return_msg", "OK");
        String uuid=prepareResults.get("out_trade_no");
        orderMapper.updateOrders(uuid);
        Cart cart = orderMapper.queryOrders(uuid);
        cartMapper.delete(cart.getShopUuid(),cart.getUserOpenid());
        webSocketTest.onMessage(cart.getShopUuid());
        return BizResponse.toXml(result);
    }


//    public Map<String,Object> order(HttpServletRequest req, String openId) throws IOException, NoSuchAlgorithmException {
//        WxUser select = wxUserMapper.select(openId);
//        Map<String, Object> result = new HashMap<>();
//        if(select == null){
//            result.put("code", "1xxx");
//            result.put("data", "请登录");
//            return result;
//        }
//
//        return result;
//
//    }
    //支付成功

    //生成24位 订单号
    public static  String getNum19(){
        String numStr = "" ;
        String trandStr = String.valueOf((Math.random() * 9 + 1) * 1000000);
        String dataStr = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
        numStr = trandStr.toString().substring(0, 4);
        numStr = numStr + dataStr ;
        return "80000"+numStr ;
    }
}
