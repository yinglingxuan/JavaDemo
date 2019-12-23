package com.example.client.controller;

import com.example.client.dao.AddressMapper;
import com.example.client.domain.Addesss;
import com.example.client.responseFile.BizResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {

    @Autowired
    AddressMapper addressMapper;

    //查询用户的地址
    @GetMapping("/getTake")
    public BizResponse query(String userOpenid){
        List<Addesss> list = addressMapper.query(userOpenid);
        return BizResponse.of(list);
    }

    @PostMapping("/addTake")
    public BizResponse add(@RequestBody Addesss addesss){
        addesss.setAddressId(BizResponse.getUUID32());
        boolean add = addressMapper.add(addesss);
        return BizResponse.of(add);
    }

    @PostMapping("/updateTake")
    public BizResponse update(@RequestBody Addesss addesss){
        boolean update = addressMapper.update(addesss);
        return BizResponse.of(update);
    }
    @PostMapping("/delete")
    public BizResponse delete(@RequestBody Addesss addesss){
        boolean delete = addressMapper.delete(addesss.getAddressId());
        return BizResponse.of(delete);
    }
}
