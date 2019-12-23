package com.example.client.domain;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Discounts {
    private Integer id;
    private String shopUuid;  //店家id
    private Integer condition;  //满足优惠的条件
    private Integer minus;     //满足后的优惠
}
