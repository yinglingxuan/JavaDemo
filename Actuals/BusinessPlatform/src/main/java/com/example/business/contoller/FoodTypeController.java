package com.example.business.contoller;

import com.alibaba.fastjson.JSONObject;
import com.example.business.entity.BizPage;
import com.example.business.entity.BizResponse;
import com.example.business.dao.FoodTypeMapper;
import com.example.business.entity.FoodType;
import com.example.business.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodTypeController {
    @Autowired
    private FoodTypeMapper foodTypeMapper;
    //   商品类目的查询
    @GetMapping("/getShopType")
    public BizResponse search(@RequestParam("shopUuid")String shopUuid,BizPage bizPage){
        List<FoodType> foodTypes = foodTypeMapper.searchAllType(shopUuid, Page.of(bizPage.getNo(), bizPage.getSize()));
        bizPage.setTotal(foodTypeMapper.searchCount(shopUuid));
        return BizResponse.of(foodTypes,bizPage);
    }
    //   商品类目的添加
    @PostMapping("/addShopType")
    public BizResponse add(@RequestBody JSONObject jsonObject){
        String shopUuid= (String) jsonObject.get("shopUuid");
        String type= (String) jsonObject.get("type");
        foodTypeMapper.add(shopUuid,type);
        return BizResponse.success();
    }
    //   商品类目的修改
    @PostMapping("/updateShop")
    public BizResponse update(@RequestBody JSONObject jsonObject){
        String shopUuid= (String) jsonObject.get("shopUuid");
        Integer type= (Integer) jsonObject.get("id");
        String newType= (String) jsonObject.get("newType");
        foodTypeMapper.update(shopUuid,type,newType);
        return BizResponse.success();
    }
    //   商品类目的删除
    @PostMapping("/deleteShop")
    public BizResponse delete(@RequestBody List<Integer> ids){
//      如果删除商品类型，则该类型下的商品也会被删除
        Integer count=foodTypeMapper.delete(ids);
        System.err.println("删除商品类型的条数："+count);
        Integer foodCount=foodTypeMapper.deleteFood(ids);
        System.err.println("删除商品的条数："+foodCount);
        return BizResponse.success();
    }
}
