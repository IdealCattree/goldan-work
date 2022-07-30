package com.ideal.golden.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ideal.golden.model.entity.TagPo;
import com.ideal.golden.model.dto.TagQueryDto;

import java.util.List;

public interface TagService extends IService<TagPo> {

    PageInfo<TagPo> list(TagQueryDto tagQuery);

    List<TagPo> allList();
}
