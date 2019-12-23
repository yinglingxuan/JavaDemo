package com.example.client.controller;

import com.example.client.responseFile.BizResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice //通知
public class ExceptionHanlder {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public BizResponse method(MethodArgumentNotValidException e){
        BindingResult bindingResul=e.getBindingResult();
        Map<String,String> map=new LinkedHashMap<>();
        bindingResul.getAllErrors().stream().forEach((o) ->{
            map.put(o.getObjectName(),o.getDefaultMessage());
        });
        return BizResponse.fail("400",map);
    }
}