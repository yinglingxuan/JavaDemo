package com.example.business.contoller;

import com.example.business.entity.BizPage;
import com.example.business.entity.BizResponse;
import com.example.business.dao.FoodMapper;
import com.example.business.entity.Food;
import com.example.business.vo.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class FoodController {
    @Autowired
    private FoodMapper foodMapper;
//商品的查询-------------opootyuytutu
    @GetMapping("/search")
    public BizResponse search(Food food,BizPage bizPage){
        List<Food> search = foodMapper.search(food, Page.of(bizPage.getNo(), bizPage.getSize()));

        bizPage.setTotal(foodMapper.searchCount(food.getShopUuid()));
        Long aLong = foodMapper.searchCount(food.getShopUuid());
        System.out.printf("count"+String.valueOf(aLong));
        return BizResponse.of(search,bizPage);
    }
//商品的添加
    @PostMapping("/addGoods")
    public BizResponse add(@RequestParam("file") MultipartFile file, Food food) {
        System.out.println(file+"进来"+food);
        if (file!=null&&file.getSize()>0){
            String fileName = UUID.randomUUID()+"_"+file.getOriginalFilename();
            File dir=new File("/imgs");
            if (!dir.exists()) dir.mkdirs();
            File fi=new File(dir+"/"+fileName);
            try{
                file.transferTo(fi);
            }
            catch(Exception e){
                System.out.println("Wrong!"+e);
            }
            fileName="http://120.79.187.50:8899/"+fileName;
            food.setFoodIcon(fileName);
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        food.setFoodUuid(uuid);
        food.setShopUuid(food.getShopUuid());
        int add = foodMapper.add(food);
        if(add==0) return BizResponse.fail();
        return BizResponse.success();
    }
//删除商品
    @PostMapping("/deleteGoods")
    public BizResponse delete(@RequestBody List<String> ids){
        int delete = foodMapper.delete(ids);
        if(delete==0) return BizResponse.fail();
        return BizResponse.success();
    }
//商品的修改
    @PostMapping("/updateGoods")
    public BizResponse update(@RequestParam("file") MultipartFile file,Food food) {
        System.err.println(food+"file="+file);
        if (file!=null&&file.getSize()>0){
            String fileName = UUID.randomUUID()+"_"+file.getOriginalFilename();
            File dir=new File("/imgs");
            if (!dir.exists()) dir.mkdirs();
            File fi=new File(dir+"/"+fileName);
            try{
                file.transferTo(fi);
            }
            catch(Exception e){
                System.out.println("Wrong!"+e);
            }
            fileName="http://120.79.187.50:8899/"+fileName;
            food.setFoodIcon(fileName);
        }
        int update = foodMapper.update(food);
        if(update==0) return BizResponse.fail();
        return BizResponse.success();
    }
    @PostMapping("/updateGoods2")
    public BizResponse update2(@RequestBody Food food) {
        System.err.println("222"+food);
        int update = foodMapper.update(food);
        if(update==0) return BizResponse.fail();
        return BizResponse.success();
    }


}
