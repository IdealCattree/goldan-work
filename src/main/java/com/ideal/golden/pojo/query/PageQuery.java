package com.ideal.golden.pojo.query;

import lombok.Data;

import java.util.List;

@Data
public class PageQuery {
    private long pageNumber = 1;

    // 页容量
    private long pageSize = 10;

    // 数据
    private List<?> data;

    // 总数据量
    private long total;

    // 总页数
    private long pages;
}
