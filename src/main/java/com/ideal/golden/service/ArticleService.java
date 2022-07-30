package com.ideal.golden.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ideal.golden.model.dto.SearchDto;
import com.ideal.golden.model.entity.ArticlePo;
import com.ideal.golden.model.dto.ArticleQueryDto;
import com.ideal.golden.model.dto.ArticleSaveDto;
import com.ideal.golden.model.vo.ArticleDetailVo;
import com.ideal.golden.model.vo.ArticleVo;

import java.util.HashMap;
import java.util.List;


public interface ArticleService extends IService<ArticlePo> {
    PageInfo<ArticleVo> recommendList(ArticleQueryDto dto);

    ArticleDetailVo getArticleDetailById(Integer id);

    PageInfo<ArticleVo> searchList(SearchDto dto);

    void removeArticle(List<String> ids);

    boolean save(ArticleSaveDto dto);

}
