package com.ideal.golden.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @作者 Ideal
 * @时间 2022-07-25 09:32
 * @类说明
 */
@Data
@Accessors(chain = true)
public class CommentsVo {
    // 评论id
    private Integer id;
    // 评论内容
    private String content;
    // 创建时间
    private Date createTime;
    // 发布评论的用户id
    private Integer userId;
    // 游客名称
    private String tName;
    // 游客邮箱
    private String tEmail;
    // 游客网站
    private String tWebsite;
    // 该评论的父id
    private Integer parentId;
    // 该评论下是否有自评论
    private List<CommentsVo> children;
}
