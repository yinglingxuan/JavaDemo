package com.example.business.contoller;

import com.example.business.dao.OrderMapper;
import com.example.business.entity.BizPage;
import com.example.business.entity.BizResponse;
import com.example.business.entity.Order;
import com.example.business.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/getOrder")
    public BizResponse search(@RequestParam("shopUuid")String shopUuid, BizPage bizPage){
        Page of = Page.of(bizPage.getNo(), bizPage.getSize());
        System.out.println("---------"+of.toString());

        List<Order> orderMasters=orderMapper.queryOrder(shopUuid, Page.of(bizPage.getNo(), bizPage.getSize()));
        bizPage.setTotal(orderMapper.queryOrderCount(shopUuid));

        System.out.printf(shopUuid+"订单总数bizPage="+bizPage);
        System.err.println(orderMasters);
        return BizResponse.of(orderMasters, bizPage);
    }
}
