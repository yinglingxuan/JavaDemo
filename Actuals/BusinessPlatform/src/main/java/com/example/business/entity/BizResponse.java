package com.example.business.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class BizResponse {
    /**
     * 响应的状态码
     */
    private String code;
    /**
     * 响应的消息
     */
    private String msg;
    /**
     * 数据响应
     */
    private Object data;
    /**
     * 当前分页
     */
    private BizPage page;

    // 状态码-描述
    private static final Map<String, String>  CODE_DESCS = new HashMap<>();
    static{
        CODE_DESCS.put("0000", "成功");
        CODE_DESCS.put("1000", "参数有误");
    }

    // 设置返回的状态码 - 自动描述
    public void setCode(String code) {
        this.code = code;
        this.msg = CODE_DESCS.get(code);
    }

    /**
     * 快捷设置方式
     * @return
     */
    public static BizResponse of(String code, Object data, BizPage page){
        BizResponse response = new BizResponse();
        response.setCode(code);
        response.setData(data);
        response.setPage(page);
        return response;
    }

    public static BizResponse of(Object data){
        return of("0000", data, null);
    }

    public static BizResponse of(Object data, BizPage page){
        return of("0000", data, page);
    }

    /**
     * 快捷设置方式
     * @return
     */
    public static BizResponse success(){
        return of("0000", null, null);
    }
    public static BizResponse fail(){
        return of("1000", "执行失败", null);
    }
    /**
     * 快捷设置方式
     * @return
     */
    public static BizResponse fail(String code, Object data){
        return of(code, data, null);
    }
}
