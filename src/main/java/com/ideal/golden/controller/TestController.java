package com.ideal.golden.controller;

import com.ideal.golden.common.utils.ResultHelper;
import com.ideal.golden.model.entity.ArticleTagPo;
import com.ideal.golden.model.result.ResultVo;
import com.ideal.golden.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ArticleTagService articleTagService;

    @PostMapping("/articleTag")
    public ResultVo test() {
        ArticleTagPo articleTagPo = new ArticleTagPo();
        articleTagPo.setTagId(3).setArticleId(4);
        articleTagService.saveOrUpdate(articleTagPo);
        return ResultHelper.ok("");
    }

    @PostMapping("/del")
    public ResultVo test2(String ids) {

        articleTagService.removeByIds(Arrays.asList(ids.split(",")));
        return ResultHelper.ok("");
    }

}
