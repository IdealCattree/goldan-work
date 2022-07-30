package com.ideal.golden.model.dto;

import lombok.Data;

@Data
public class ArticleQueryDto extends PageQueryDto {
    // 标签id
    private Integer tagId;
    // 类别id
    private Integer typeId;
    // 用户id
    private Integer userId;
    // 排序规则 1综合排序 2时间排序 3点赞量排序
    private Integer sort = 1;
}
