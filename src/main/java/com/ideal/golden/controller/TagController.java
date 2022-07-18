package com.ideal.golden.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ideal.golden.pojo.po.Tag;
import com.ideal.golden.pojo.query.TagQuery;
import com.ideal.golden.pojo.result.R;
import com.ideal.golden.service.TagService;
import com.ideal.golden.utils.Rs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController extends BaseController<Tag> {
    @Autowired
    private TagService tagService;

    @GetMapping("/allList")
    public R allList() {
        List<Tag> tags = tagService.allList();
        return Rs.ok(tags);
    }

    @GetMapping("/list")
    public R list(TagQuery query) {
        tagService.list(query);
        return Rs.ok(query);
    }


    @Override
    protected IService<Tag> getService() {
        return tagService;
    }
}
