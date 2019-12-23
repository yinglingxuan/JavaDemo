package com.example.client.controller;

import com.example.client.dao.UserInfosMappers;
import com.example.client.domain.UserInfos;
import com.example.client.responseFile.BizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class saveUserMapController {

    @Autowired
    private UserInfosMappers userInfosMappers;
    @GetMapping("/localMap")
    public BizResponse saveMap(UserInfos userInfos){
        System.err.println("修改"+userInfos);

        userInfosMappers.updateMap(userInfos);
        return BizResponse.success();
    }
}
