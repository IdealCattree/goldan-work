package com.ideal.golden.controller;

import com.github.pagehelper.PageInfo;
import com.ideal.golden.common.utils.ResultHelper;
import com.ideal.golden.model.dto.SearchDto;
import com.ideal.golden.model.result.ResultVo;
import com.ideal.golden.model.vo.ArticleVo;
import com.ideal.golden.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @作者 Ideal
 * @时间 2022-07-26 14:34
 * @类说明
 */
@RestController
public class SearchController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/search")
    public ResultVo searchArticle(SearchDto dto) {
        if (dto.getSearchType() == null) dto.setSearchType(1);

        if (dto.getKeyword() == null) return ResultHelper.ok(new ArrayList<>());

        Integer type = dto.getSearchType();
        if (type == 1) { // 文章
            PageInfo<ArticleVo> articleVoPageInfo = articleService.searchList(dto);
            return ResultHelper.ok(articleVoPageInfo);
        }
        return null;
    }
}
