package com.example.client.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserInfos {
    private String userOpenid;//微信openid
    private String userName;//用户昵称
    private String userSex;//用户性别
    private String userCountry;//用户国籍
    private String userLanguage;//所用语言
    private String userProvince;//用户省份
    private String userCity;//用户城市
    private double userLatitude;//纬度
    private double userLongitude;//经度
    private String userHeads;//用户头像
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd-HH:mm")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd-HH:mm")
    private Date updateTime;


}
