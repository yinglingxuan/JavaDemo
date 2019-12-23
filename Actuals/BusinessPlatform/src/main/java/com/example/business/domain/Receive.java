package com.example.business.domain;

import lombok.Data;
import lombok.ToString;

//用户收藏实体类
@Data
@ToString
public class Receive {
    private String id;
    private String openid;
    private String uuid;
}
