package com.example.business.domain;

import lombok.Data;

@Data
public class ShopUser {
    private String shopUuid;  //商家id 也是账号
    private String shopUserName;//商家用户名
    private String shopPassword;//商家密码
    private String shopName;//商家名

    private String newPassword;//商家新密码
}
