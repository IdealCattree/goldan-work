package com.ideal.golden.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ideal.golden.model.entity.TagPo;
import com.ideal.golden.model.dto.TagQueryDto;
import com.ideal.golden.model.result.ResultVo;
import com.ideal.golden.service.TagService;
import com.ideal.golden.common.utils.ResultHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController extends BaseController<TagPo> {
    @Autowired
    private TagService tagService;

    @GetMapping("/allList")
    public ResultVo allList() {
        List<TagPo> tags = tagService.allList();
        return ResultHelper.ok(tags);
    }

    @GetMapping("/list")
    public ResultVo list(TagQueryDto query) {
        PageInfo<TagPo> list = tagService.list(query);
        return ResultHelper.ok(list);
    }


    @Override
    protected IService<TagPo> getService() {
        return tagService;
    }
}
