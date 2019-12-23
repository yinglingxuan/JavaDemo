package com.example.business.contoller;

import com.example.business.dao.DisMapper;
import com.example.business.domain.Discounts;
import com.example.business.entity.BizPage;
import com.example.business.entity.BizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DisController {
    @Autowired
    public DisMapper disMapper;

    @GetMapping("/getDis")
    public BizResponse get(Discounts discounts,BizPage bizPage){
        //获取到优惠信息
        List<Discounts> selects = disMapper.selects(discounts.getShopUuid());
        long l=10;
        bizPage.setTotal(l);
        return BizResponse.of(selects,bizPage);
    }

    //添加优惠
    @PostMapping("/addDis")
    public BizResponse add(@RequestBody Discounts discounts){
        //添加优惠信息
        disMapper.addDis(discounts);
        /*System.out.println(discounts);*/
        return BizResponse.success();
    }



    //修改优惠信息
    @PostMapping("/upDataDis")
    public BizResponse upDataDis(@RequestBody Discounts discounts){
        disMapper.upDataDis(discounts);
        /*System.out.println(discounts);*/
        return BizResponse.success();
    }

    //删除优惠信息
    @PostMapping("/DeleteDis")
    public BizResponse DeleteDis(@RequestBody List<Integer> ids){
        /*System.out.println(ids);*/
        disMapper.DeleteDis(ids);
        return BizResponse.success();
    }

}
