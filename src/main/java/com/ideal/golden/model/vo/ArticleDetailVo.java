package com.ideal.golden.model.vo;

import com.ideal.golden.model.entity.TagPo;
import com.ideal.golden.model.entity.TypePo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @作者 weidian
 * @时间 2022-07-25 09:23
 * @类说明
 */
@Data
@Accessors(chain = true)
public class ArticleDetailVo {
    // id主键
    private Integer id;
    // 文章标题
    private String title;
    // 点赞量
    private Integer like;
    // 浏览量
    private Integer view;
    // 创建人id
    private Integer userId;
    // 文章内容
    private String content;
    // 创建时间
    private Date createTime;
    // 最后一个更新时间
    private Date updateTime;
    // 文章封面
    private String image;
    // 文章类型id
    private TypePo typePo;
    // 文章标签
    private List<TagPo> tags;
    // 文章评论
    private List<CommentsVo> commentsVos;
}
