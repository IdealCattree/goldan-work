package com.ideal.golden.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ideal.golden.model.entity.ArticleTagPo;
import com.ideal.golden.model.entity.TagPo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagMapper extends BaseMapper<ArticleTagPo> {

    List<TagPo> selectTagListByArticleId(Integer id);
}
