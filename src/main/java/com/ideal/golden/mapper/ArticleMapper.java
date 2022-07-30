package com.ideal.golden.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ideal.golden.model.dto.ArticleQueryDto;
import com.ideal.golden.model.dto.SearchDto;
import com.ideal.golden.model.entity.ArticlePo;
import com.ideal.golden.model.vo.ArticleVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper extends BaseMapper<ArticlePo> {

    List<ArticleVo> recommendList(ArticleQueryDto dto);

    List<ArticleVo> searchArticleList(SearchDto dto);

}
