package com.example.client.domain;

import lombok.Data;

@Data
public class Pay {
    private String orderId;//订单id
    private String addressId;//地址id
    private String remark;//备注
    private String openId;//用户id
    private String uuid;//红包id
}
