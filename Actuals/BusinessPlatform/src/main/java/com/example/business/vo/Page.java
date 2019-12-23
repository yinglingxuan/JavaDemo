package com.example.business.vo;

import lombok.Data;

@Data
public class Page {//分页的起始位置和条数
    private Integer offset = 0;
    private Integer limits = 5;

    private Page() {

    }

    private Page(int no, int size) {
//        注意：在layui上的分页，第一页是默认为1
            this.offset = (no-1) * size;
            this.limits = size;

    }

    public static Page of(int no, int size) {
        return new Page(no, size);
    }

}
