package com.example.client.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ShopComment{
    private String id;
    private String shopUuid;//商家的Uuid
    private String userName;//用户名
    private String userHeader;//用户头像
    private String text;//评论的内容
    private Byte type;//评论的类型   0/1 差评/好评
    private double score;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date updateTime;
}
