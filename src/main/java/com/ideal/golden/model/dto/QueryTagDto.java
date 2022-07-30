package com.ideal.golden.model.dto;

import com.ideal.golden.model.entity.TagPo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @作者 Ideal
 * @时间 2022-07-27 22:09
 * @类说明 根据文章id查询 标签的转换对象
 */
@Data
@Accessors(chain = true)
public class QueryTagDto {
    private Integer articleId;
    private List<TagPo> tagPoList;
}
