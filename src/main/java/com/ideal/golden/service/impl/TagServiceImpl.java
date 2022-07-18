package com.ideal.golden.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ideal.golden.dao.TagMapper;
import com.ideal.golden.enhance.MpPage;
import com.ideal.golden.enhance.MpQueryWrapper;
import com.ideal.golden.pojo.po.Tag;
import com.ideal.golden.pojo.query.TagQuery;
import com.ideal.golden.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public void listById(TagQuery tagQuery) {
        MpQueryWrapper<Tag> wrapper = new MpQueryWrapper<>();
        wrapper.in(Tag::getId, Arrays.asList(tagQuery.getIds().split(",")));
        baseMapper.selectList(wrapper);
    }

    @Override
    public void list(TagQuery tagQuery) {
        MpQueryWrapper<Tag> wrapper = new MpQueryWrapper<>();
        baseMapper.selectPage(new MpPage<>(tagQuery), wrapper).updateQuery(tagQuery);
    }

    @Override
    public List<Tag> allList() {
        MpQueryWrapper<Tag> wrapper = new MpQueryWrapper<>();
        wrapper.orderByAsc(Tag::getId);
        return baseMapper.selectList(wrapper);
    }
}
