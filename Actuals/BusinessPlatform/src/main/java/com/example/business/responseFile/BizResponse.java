package com.example.business.responseFile;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class BizResponse {
    private String code;   //当前的状态码
    private String msg;    //当前的状态信息
    private Object data;   //当前的数据
    private BizPage page;  //分页的数据


    private static final Map<String,String> CODE_DESCS= new HashMap<>();
    static{ //默认静态状态码信息
        CODE_DESCS.put("0000","成功");
        CODE_DESCS.put("400","参数错误");
        CODE_DESCS.put("0404","输入的登录信息有误");
        CODE_DESCS.put("3000","输入的密码错误");
        CODE_DESCS.put("0200","账号已经存在请更换");
    }
    // 设置错误码 - 自动描述
    public void setCode(String code) {
        this.code = code;     /*接收闯进来的状态码*/
        this.msg = CODE_DESCS.get(code);    /*根据状态码获取状态信息*/
    }
    //根据不同的情况响应不同的数据
    public static BizResponse of(String code, Object data,BizPage page){
        BizResponse bizResponse = new BizResponse(); //当前的对象
        bizResponse.setCode(code);  // 设置状态码信息
        bizResponse.setData(data);  //传入数据
        bizResponse.setPage(page);  //分页的数据
        return bizResponse;
    }

    public static BizResponse of(Object data, BizPage page){   //获取有分页的默认成功数据
        return of("0000", data, page);
    }
    //生成32uuid
    public static String getUUID32(){
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() ;
    }

    /*获取到数据的情况*/
    public static BizResponse of(Object data){

        return of("0000",data,null);
    }
    //修改或者删除/添加成功后
    public static BizResponse success(){
        return of("0000","成功",null);
    }

    //失败后的错误信息
    //传入对应的失败状态码和失败说明
    public static BizResponse fail(String code, Object data){
        return of(code,data,null);
    }
    //根据不同的情况响应不同的数据
    public static BizResponse of(String code, Object data){
        BizResponse bizResponse = new BizResponse(); //当前的对象
        bizResponse.setCode(code);  // 设置状态码信息
        bizResponse.setData(data);
        return bizResponse;
    }

    public static BizResponse fail(String code){
        return of(code,null,null);
    }
}
