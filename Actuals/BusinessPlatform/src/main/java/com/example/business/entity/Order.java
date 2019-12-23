package com.example.business.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order{
    private String id;//订单号uuid
    private String userOpenid;//用户的openid
    private String remark;//备注
    private byte orderState;//支付的状态：0 或 1
    private String addressId;//用户的地址id
    private Date createTime;
    private Date updateTime;

//    private List<Food> food;
//    private UserAddress userAddress;//用户地址
/*
    8点击支付后 发送订单号

| 参数     | 类型   | 默认值 | 备注   |
            | -------- | ------ | ------ | ------ |
            | shopuuid | String | null   | 店铺id |

            #### 请求的URL   GET方法

/getOrder?shopuuid

#### 返回的数据

```json
    {	"code": "0000",
            "msg": "成功",
    “data”:{
        id:"订单号",
                userOpenid:"用户的openid",
                remark:"备注",
                order_state:"订单状态"
        foods:[{

                 private String

            id:"商品id",
                    type:"商品类型",
                    name:"商品名称",
                    description: " 商品描述",
                    price:"优惠价格",
                    old_price:"原价格",
                    info:"价格",
                    icon:"商品图片",
                    count:"商品数量",
        }]
        addressId："地址的id"
        userOpenid:"用户的openid",
                userName:"用户的姓名",
                userPhone:"用户的电话",
                userSex: "用户的性别"
        userAddress:"用户的地址"
        addressDoorplate:"门牌号"
        addressTitle:"标签"
     }
    }
*/


}
