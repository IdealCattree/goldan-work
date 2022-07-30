package com.ideal.golden.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ideal.golden.common.foreign.anno.ForeignField;
import com.ideal.golden.common.foreign.anno.ForeignTable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @作者 Ideal
 * @时间 2022-07-19 22:47
 * @类名 ArticleTagPo
 * @方法说明 文章和标签的映射表
 */
@Data
@TableName("k_a_tag")
@Accessors(chain = true)
@ForeignTable("k_a_tag")
public class ArticleTagPo {
    private Integer id;
    @ForeignField(mainTable = ArticlePo.class, mainField = "id", column = "article_id")
    private Integer articleId;
    @ForeignField(mainTable = TagPo.class, column = "tag_id")
    private Integer tagId;
}
