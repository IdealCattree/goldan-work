package com.ideal.golden.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@TableName("comments")
public class CommentsPo {
    // 评论id
    private Integer id;
    // 内容
    private String content;
    // 父级id，默认为0
    private Integer parentId;
    // 文章id
    private Integer articleId;
    // 创建时间
    private Date createTime;
    // userid
    private Integer userId;
    // 游客名称
    private String tName;
    // 游客邮箱
    private String tEmail;
    // 游客网站
    private String tWebsite;
    // 顶级评论id
    private Integer topId;

}
