package com.ideal.golden.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ideal.golden.model.entity.CommentsPo;
import com.ideal.golden.model.vo.CommentsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsMapper extends BaseMapper<CommentsPo> {

    List<CommentsVo> selectListByArticleId(Integer id);
}
