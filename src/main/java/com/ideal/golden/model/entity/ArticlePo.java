package com.ideal.golden.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ideal.golden.common.foreign.anno.ForeignCascade;
import com.ideal.golden.common.foreign.anno.ForeignField;
import com.ideal.golden.common.foreign.anno.ForeignTable;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
@TableName("article")
@Accessors(chain = true)
@ForeignTable("article")
public class ArticlePo {
    // id主键
    @ForeignField(column = "id", cascade = ForeignCascade.DELETE)
    private Integer id;
    // 文章标题
    private String title;
    // 文章内容
    private String content;
    // 点赞量
    @TableField("`like`")
    private Integer like;
    // 浏览量
    private Integer view;
    // 创建人id
    private Integer userId;
    // 文章摘要
    private String aAbstract;
    // 创建时间
    private Date createTime;
    // 最后一个更新时间
    private Date updateTime;
    // 文章类型id
    private Integer typeId;
    // 文章封面
    private String image;


}
