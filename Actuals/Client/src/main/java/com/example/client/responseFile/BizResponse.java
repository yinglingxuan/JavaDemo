package com.example.client.responseFile;

import com.example.client.controller.XmlHelper;
import com.example.client.domain.ShopData;
import lombok.Data;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Data
public class BizResponse {
    private String code;   //当前的状态码
    private String msg;    //当前的状态信息
    private Object data;   //当前的数据
    private BizPage page;

    private static final Map<String,String> CODE_DESCS= new HashMap<>();
    static{ //默认静态状态码信息
        CODE_DESCS.put("0000","成功");
        CODE_DESCS.put("400","参数错误");
        CODE_DESCS.put("4001","签到失败，请明天再来");
        CODE_DESCS.put("4002","兑换失败，积分不足");
        CODE_DESCS.put("4003","领取失败，请消费上一张优惠券");
    }


    // 设置错误码 - 自动描述
    public void setCode(String code) {
        this.code = code;     /*接收闯进来的状态码*/
        this.msg = CODE_DESCS.get(code);    /*根据状态码获取状态信息*/
    }
    //根据不同的情况响应不同的数据
        public static BizResponse of(String code, Object data){
        BizResponse bizResponse = new BizResponse(); //当前的对象
        bizResponse.setCode(code);  // 设置状态码信息
        bizResponse.setData(data);
        return bizResponse;
    }

    /*获取到数据的情况*/
    public static BizResponse of(Object data){

        return of("0000",data);
    }
    //修改或者删除/添加成功后
    public static BizResponse success(){
        return of("0000","成功");
    }

    //失败后的错误信息
    //传入对应的失败状态码和失败说明
    public static BizResponse fail(String code, Object data){
        return of(code,data);
    }

    //生成32uuid
    public static String getUUID32(){
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() ;
    }

    //调用支付
    //1.是支付金额     2.生成的uuid     3.openid   4.反向代理  5.支付成功通知地址
    public static Map<String, Object> payment(double price,String uuid,String openId, HttpServletRequest req,String url) throws IOException, NoSuchAlgorithmException {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> paras = new TreeMap<>();
        paras.put("appid", "wx0f0a4bbe2fc2fc3a");
        paras.put("mch_id", "1508262781");
        paras.put("nonce_str", UUID.randomUUID().randomUUID().toString().replaceAll("-", ""));
        paras.put("out_trade_no",uuid);
        paras.put("body", "商品描述");
        paras.put("total_fee", Math.round(1));
        paras.put("openid",openId);
        paras.put("trade_type", "JSAPI");
        paras.put("notify_url",url);
        paras.put("spbill_create_ip",req.getRemoteAddr()); // 反向代理(代理服务器)
        String sign = generateSign(paras);
        paras.put("sign", sign);
        OkHttpClient client = new OkHttpClient();
        String xml = toXml(paras);
        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("text/xml"), toXml(paras));
        Request request = new Request.Builder().url("https://api.mch.weixin.qq.com/pay/unifiedorder").post(body).build();

        Response response = client.newCall(request).execute();
        String resultXml = response.body().string();
        XmlHelper xmlHelper = XmlHelper.of(resultXml);
        Map<String, String> prepareResults = xmlHelper.toMap();

        Map<String, String> jsApiParas = new TreeMap<>();
        jsApiParas.put("appId", "wx0f0a4bbe2fc2fc3a");
        jsApiParas.put("timeStamp", System.currentTimeMillis()/1000 + "");
        jsApiParas.put("nonceStr", System.currentTimeMillis()+"");
        jsApiParas.put("package", "prepay_id="+prepareResults.get("prepay_id"));
        jsApiParas.put("signType", "MD5");
        jsApiParas.put("paySign", generateSign(jsApiParas));
        result.put("code", "0");
        result.put("data", jsApiParas);
        return  result;
    }

    public static String generateSign(Map<String, ?> paras) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 字符拼接
        StringBuffer sign = new StringBuffer();
        paras.forEach((k, v) -> sign.append(k).append("=").append(v).append("&"));
        sign.append("key=").append("lakJYzxp5znq5Pz1JYDBGInzrUNVAeYj");
        // MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(sign.toString().getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
    public static String toXml(Map<String, Object> params) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key   = entry.getKey();
            String value = String.valueOf(entry.getValue());
            // 略过空值
            if (StringUtils.isEmpty(value)) continue;
            xml.append("<").append(key).append(">");
            xml.append(entry.getValue());
            xml.append("</").append(key).append(">");
        }
        xml.append("</xml>");
        return xml.toString();
    }

    public static BizResponse of(String code, Object data, BizPage page){
        BizResponse response = new BizResponse();
        response.setCode(code);
        response.setData(data);
        response.setPage(page);
        return response;
    }

    public static BizResponse of(List<?> data, BizPage page){
        if (data!=null&&data.size()>0) return of("0000", data, page);
        return of("1111","查询内容为空" , page);
    }

    public static BizResponse fail(String code){
        return of(code,null,null);
    }
}
