package com.ideal.golden.model.dto;

import lombok.Data;

/**
 * @作者 weidian
 * @时间 2022-07-26 14:42
 * @类说明
 */
@Data
public class SearchDto extends KeywordQueryDto {
    // 搜索的类型  1 文章  2 用户   3 标签  ...
    private Integer searchType;
    // 排序方式
    private Integer sort;
}
