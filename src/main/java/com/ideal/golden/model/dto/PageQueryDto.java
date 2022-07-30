package com.ideal.golden.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageQueryDto {
    private int pageNumber = 1;

    // 页容量
    private int pageSize = 10;

    // 数据
    private List<?> data;

    // 总数据量
    private long total;

    // 总页数
    private long pages;

    private int offSet;

    public int getOffSet() {
        return (this.pageNumber - 1) * this.pageSize;
    }
}
