package com.example.client.responseFile;

import lombok.Data;

@Data
public class Page {
    private Integer offset = 0;
    private Integer limits = 20;

    private Page() {
    }

    private Page(int no, int size) {
        this.offset = (no - 1) * size;
        this.limits = size;
    }

    public static Page of(int no, int size) {
        return new Page(no, size);
    }

}
