package com.example.client.controller;

import com.example.client.dao.ShopCommentMapper;
import com.example.client.domain.ShopComment;
import com.example.client.responseFile.BizPage;
import com.example.client.responseFile.BizResponse;
import com.example.client.responseFile.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ShopCommentController {
    @Autowired
    private ShopCommentMapper shopCommentMapper;
    @GetMapping("/addShopCom")
    public BizResponse add(ShopComment shopComment){
        System.err.println(shopComment+"===添加bizpage=");
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        shopComment.setId(uuid);
        Byte type=shopComment.getScore()>=3?(byte) 1:(byte) 0;//根据用户的星星个数区分是好评、差评
        shopComment.setType(type);
        int add = shopCommentMapper.add(shopComment);
        return BizResponse.success();
    }
    @GetMapping("/queryShopCom")
    public BizResponse query(ShopComment shopComment, BizPage bizPage){
        List<ShopComment> query = shopCommentMapper.query(shopComment, Page.of(bizPage.getNo(), bizPage.getSize()));
        bizPage.setTotal(shopCommentMapper.allCom(shopComment));
        return BizResponse.of(query,bizPage);
    }
}
