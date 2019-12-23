package com.example.client.controller;

import com.example.client.dao.FoodCommentMapper;
import com.example.client.domain.FoodComment;
import com.example.client.responseFile.BizPage;
import com.example.client.responseFile.BizResponse;
import com.example.client.responseFile.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class FoodCommentController {
    @Autowired
    private FoodCommentMapper foodCommentMapper;

    @GetMapping("/addFoodCom")
    public BizResponse add(FoodComment foodComment){
        System.err.println(foodComment+"===添加bizpage=");
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        foodComment.setId(uuid);
        Byte type=foodComment.getScore()>=3?(byte) 1:(byte) 0;//根据用户的星星个数区分是好评、差评
        foodComment.setType(type);
        System.err.println(foodComment+"type="+foodComment.getType()+"\t\t"+foodComment.getScore());
        int add = foodCommentMapper.add(foodComment);
        return BizResponse.success();
    }

    @GetMapping("/queryFoodCom")
    public BizResponse query(FoodComment foodComment, BizPage bizPage){
        System.err.println(foodComment+"===查询bizpage="+bizPage);
        List<FoodComment> query = foodCommentMapper.query(foodComment, Page.of(bizPage.getNo(), bizPage.getSize()));
        bizPage.setTotal(foodCommentMapper.allCom(foodComment));
        return BizResponse.of(query,bizPage);
    }
}
