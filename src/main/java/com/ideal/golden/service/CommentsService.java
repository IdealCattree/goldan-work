package com.ideal.golden.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ideal.golden.model.entity.CommentsPo;

public interface CommentsService extends IService<CommentsPo> {
    boolean save(CommentsPo commentsPo);
    boolean remove(String id);
    boolean removeCommentsByArticle(String articleId);
}
