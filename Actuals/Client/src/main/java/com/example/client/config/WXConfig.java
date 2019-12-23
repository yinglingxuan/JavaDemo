package com.example.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.util.Map;

//@Component
@Configuration
@ConfigurationProperties("wx.config")
@Data
public class WXConfig {
    private String appId;
    private String appSecret;
    private String url;
    private Map<String, String> templates;

    /**<pre>
     * 网页授权
     * @author 潘清山
     * @since 1.0
     * @since JDK 8.0
     * </pre>
     */
    public String getOauth2Authorize(String redirectUrl, String state, String... scope) {
        String url = UriComponentsBuilder.fromUriString("https://open.weixin.qq.com/connect/oauth2/authorize")
                .queryParam("appid", appId)
                .queryParam("redirect_uri", UriUtils.encode(redirectUrl, "UTF-8"))
                .queryParam("response_type", "code")
                .queryParam("scope", scope.length == 0 ? "snsapi_userinfo" : scope[0])
                .queryParam("state", state)
                .fragment("wechat_redirect").build().toString();
        return url;
    }

    /**<pre>
     * code -> 获取ak
     * @author 潘清山
     * @since 1.0
     * @since JDK 8.0
     * </pre>
     */
    public String getOauth2AK(String code) {
        return String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
                appId, appSecret, code);
    }

    /**<pre>
     * code -> ak -> 用户信息
     * @author 潘清山
     * @since 1.0
     * @since JDK 8.0
     * </pre>
     */
    public String getOauth2UserInfo(String ak, String openId) {
        return String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN", ak, openId);
    }

    /**
     * 后台AccessToken专用(URL)
     * @return
     */
    public String getAccessToke(){
        return String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId, appSecret);
    }

    /**<pre>
     * 获取模板ID
     * @author 潘清山
     * @since 1.0
     * @since JDK 8.0
     * </pre>
     */
    public String getTemplateId(String templateName){
        return  templates.get(templateName);
    }

}
