package com.ideal.golden.controller;

import com.github.pagehelper.PageInfo;
import com.ideal.golden.model.dto.ArticleQueryDto;
import com.ideal.golden.model.dto.ArticleSaveDto;
import com.ideal.golden.model.result.ResultVo;
import com.ideal.golden.model.vo.ArticleDetailVo;
import com.ideal.golden.model.vo.ArticleVo;
import com.ideal.golden.service.ArticleService;
import com.ideal.golden.common.utils.ResultHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@RestController
@RequestMapping("/articles")
public class ArticleController  {

    @Autowired
    private ArticleService service;

    /**
     * 根据query查询
     * @param dto
     * @return 推荐列表
     */
    @GetMapping("/recommendList")
    public ResultVo recommendList(ArticleQueryDto dto) {
        PageInfo<ArticleVo> articleVoList = service.recommendList(dto);
        return ResultHelper.ok(articleVoList);
    }


    /**
    * @作者: Ideal
    * @方法名: getArticleById
    * @说明: 根据文章id请求详情内容
    * @时间: 2022/7/19 12:01
    * @param id:
    * @return com.ideal.golden.pojo.result.R
    */
    @GetMapping("/{id}")
    public ResultVo getArticleById(@PathVariable Integer id) {
        ArticleDetailVo article = service.getArticleDetailById(id);
        return ResultHelper.ok(article);
    }

    /**
    * @作者: Ideal
    * @方法名: save
    * @说明: 添加文章
    * @时间: 2022/7/19 13:27
    * @param dto: 请求dto
    * @return com.ideal.golden.pojo.result.ResultVo
    */
    @PostMapping("/save")
    public ResultVo save(@RequestBody ArticleSaveDto dto) {
        return service.save(dto) ? ResultHelper.ok("添加成功") : ResultHelper.error("添加失败");

    }

    /**
    * @作者: Ideal
    * @方法名: remove
    * @说明: 根据ids删除文章
    * @时间: 2022/7/19 15:59
    * @param :
    * @return com.ideal.golden.pojo.result.ResultVo
    */
    @DeleteMapping("/remove")
    public ResultVo remove(String ids) {
        service.removeArticle(Arrays.asList(ids.split(",")));
        return ResultHelper.ok("删除成功");
    }
}
