package com.example.business.entity;

import lombok.Data;

@Data
public class BizPage {
    /**
     * 总数
     */
    private Long total;
    /**
     * 当前页
     */
    private Integer no = 1;
    /**
     * 每页多少
     */
    private Integer size = 10;

    /**
     * 快捷设置方式
     * @return
     */
    public static BizPage of(Long total, Integer no, Integer size){
        BizPage page = new BizPage();
        page.setNo(no);
        page.setSize(size);
        page.setTotal(total);
        return page;
    }

}
