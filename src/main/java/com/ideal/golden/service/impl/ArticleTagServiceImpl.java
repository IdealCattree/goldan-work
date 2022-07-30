package com.ideal.golden.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ideal.golden.mapper.ArticleTagMapper;
import com.ideal.golden.model.entity.ArticleTagPo;
import com.ideal.golden.service.ArticleTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @作者 Ideal
 * @时间 2022-07-21 19:43
 * @类说明 文章与标签的映射表service实现
 */
@Service
@Transactional
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTagPo> implements ArticleTagService {

    @Override
    public boolean removeByArticleId(String articleId) {

        LambdaQueryChainWrapper<ArticleTagPo> wrapper = lambdaQuery().eq(ArticleTagPo::getArticleId, articleId);
        int delete = baseMapper.delete(wrapper);
        return delete > 0;
    }
}
