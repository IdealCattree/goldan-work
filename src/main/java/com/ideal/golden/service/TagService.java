package com.ideal.golden.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ideal.golden.pojo.po.Tag;
import com.ideal.golden.pojo.query.TagQuery;

import java.util.List;

public interface TagService extends IService<Tag> {
    void listById(TagQuery tagQuery);

    void list(TagQuery tagQuery);

    List<Tag> allList();
}
