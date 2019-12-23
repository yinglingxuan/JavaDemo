package com.example.client.responseFile;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BizPage {
    private Long total; //总数,
    private Integer no; /*当前页,*/
    private Integer size; /*每页多少*/

    //相当于构造器，传入分页的数据
    public static BizPage of(Long total, Integer no, Integer size){
        BizPage page = new BizPage();
        page.setNo(no);
        page.setSize(size);
        page.setTotal(total);
        return page;
    }


}
