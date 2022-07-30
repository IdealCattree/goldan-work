package com.ideal.golden.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArticleSaveDto {
    // id主键
    private Integer id;
    // 文章标题
    private String title;
    // 文章内容
    private String content;
    // 创建人id
    private Integer userId;
    // 文章摘要
    private String brief;
    // 文章封面
    private String image;
    // 文章类型
    private Integer typeId;
    // 文章标签
    private List<Integer> tags;

}
