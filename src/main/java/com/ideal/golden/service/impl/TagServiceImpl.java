package com.ideal.golden.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ideal.golden.mapper.TagMapper;
import com.ideal.golden.common.enhance.MpPage;
import com.ideal.golden.common.enhance.MpQueryWrapper;
import com.ideal.golden.model.entity.TagPo;
import com.ideal.golden.model.dto.TagQueryDto;
import com.ideal.golden.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl extends ServiceImpl<TagMapper, TagPo> implements TagService {

    /**
    * @作者: Ideal
    * @说明: 分页查询标签
    * @时间: 2022/7/29 17:55
    * @param tagQuery:
    * @return com.github.pagehelper.PageInfo<com.ideal.golden.model.entity.TagPo>
    */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<TagPo> list(TagQueryDto tagQuery) {

        PageHelper.startPage(tagQuery.getPageNumber(), tagQuery.getPageSize());
        PageInfo<TagPo> tagPoPageInfo = new PageInfo<>(lambdaQuery().list());
        return tagPoPageInfo;

    }
}
