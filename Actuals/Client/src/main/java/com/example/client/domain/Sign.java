package com.example.client.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Sign {
    private String openid;
    private Integer sky;
    private Integer integral;
    private String updateTime;
}
