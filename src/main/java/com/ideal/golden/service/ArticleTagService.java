package com.ideal.golden.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ideal.golden.model.entity.ArticleTagPo;

public interface ArticleTagService extends IService<ArticleTagPo> {
    boolean removeByArticleId(String articleId);
}
