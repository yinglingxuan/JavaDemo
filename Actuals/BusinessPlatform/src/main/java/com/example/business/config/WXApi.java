package com.example.business.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class WXApi {
    @Autowired
    private WXConfig wxConfig;

    private final OkHttpClient client = new OkHttpClient();
    public static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    // private final Map<String, String> openId2AK = new HashMap<>();

    public String getUserInfoWithCode(String code) {
        // 获取用户信息
        String oauth2AK = wxConfig.getOauth2AK(code);
        System.err.println("acess_222token获取"+oauth2AK);
        // code -> at -> userinfo
        String at = get(oauth2AK);
        JSONObject atJson = JSON.parseObject(at);
        String ak = atJson.getString("access_token");
        String openId = atJson.getString("openid");
        System.err.println(at+"\t"+ak+"\t"+openId);
        System.err.println(ak+"\t"+openId);
        return get(wxConfig.getOauth2UserInfo(ak, openId));
    }

    /**
     * 获取有效at(如果过期则自动刷新)
     * @return
     */
    public String getAccessToken(){
        // {"access_token":"ACCESS_TOKEN","expires_in":7200}
        String string = get(wxConfig.getAccessToke());
        JSONObject json = JSON.parseObject(string);
        return json.getString("access_token");
    }

//    public String sendTemplate(String openId, Map<String, String> param, String templateName){
//        String templateId = wxConfig.getTemplateId(templateName);
//
//    }

    public  String get(String url) {
        Request request = new Request.Builder().url(url).get().build();
        return execute(request);
    }

    private String post(String url, String json){
        RequestBody body = RequestBody.create(TYPE_JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        return execute(request);
    }

    //日志的配置
    private String execute(Request request){
        // 增加日志
        try (Response response = client.newCall(request).execute()) {
            String data = response.body().string();
            log.debug("API Res: {}\n", response);
            log.trace("API Res: {}\n", response, data);
            return data;
        } catch (IOException e) {
            log.warn("API Req:{} \n=> \nRes:{}", request, e);
            throw new IllegalArgumentException(e);
        }
    }
}
