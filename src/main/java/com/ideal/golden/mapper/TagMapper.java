package com.ideal.golden.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ideal.golden.model.dto.QueryTagDto;
import com.ideal.golden.model.entity.TagPo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagMapper extends BaseMapper<TagPo> {

    List<QueryTagDto> getTagListByArticleId(List<Integer> articleIds);
}
