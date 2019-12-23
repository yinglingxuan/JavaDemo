package com.example.business.contoller;

import com.example.business.dao.ShopMessageMappers;
import com.example.business.domain.ShopData;
import com.example.business.responseFile.BizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
public class ShopMessageController {
    @Autowired
    private ShopMessageMappers shopMessageMappers ;

    @GetMapping("/getShopMessage")
    public BizResponse search(String uuid){
        ShopData shopMessage = shopMessageMappers.getShopMessage(uuid);
        return BizResponse.of(shopMessage);
    }


    @PostMapping("/updateShops")
    public BizResponse updateShops(@RequestBody ShopData shop){
        /*System.out.println(shop);*/
        shopMessageMappers.updateShop(shop);
        return BizResponse.success();
    }


    @PostMapping("/updateShops2")
    public BizResponse updateShops2(@RequestParam("file") MultipartFile file, ShopData shop){
        /*System.out.println(file+"----------"+shop);*/
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
            shop.setShopIcon(fileName);
        }
        shopMessageMappers.updateShop(shop);
        return BizResponse.success();
    }



    //修改密码


}
