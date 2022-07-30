package com.ideal.golden.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ideal.golden.mapper.*;
import com.ideal.golden.common.enhance.MpQueryWrapper;
import com.ideal.golden.model.dto.QueryTagDto;
import com.ideal.golden.model.dto.SearchDto;
import com.ideal.golden.model.entity.*;
import com.ideal.golden.model.dto.ArticleQueryDto;
import com.ideal.golden.model.dto.ArticleSaveDto;
import com.ideal.golden.model.vo.ArticleDetailVo;
import com.ideal.golden.model.vo.ArticleVo;
import com.ideal.golden.model.vo.CommentsVo;
import com.ideal.golden.service.ArticleService;
import com.ideal.golden.service.ArticleTagService;
import com.ideal.golden.service.CommentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticlePo> implements ArticleService {

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TypeMapper typeMapper;

    /**
     * @param dto: 查询参数
     * @return void
     * @作者: Ideal
     * @说明: 推荐列表
     * @时间: 2022/7/19 15:55
     */
    @Override
    public PageInfo<ArticleVo> recommendList(ArticleQueryDto dto) {
        // 查询文章
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        PageInfo<ArticleVo> articleVoPageInfo = new PageInfo<>(baseMapper.recommendList(dto));

        List<Integer> articleIdList = articleVoPageInfo.getList()
                .stream()
                .map((articleVo -> articleVo.getId()))
                .collect(Collectors.toList());

        // 根据文章id查询文章的标签
        List<QueryTagDto> tagListByArticleId = tagMapper.getTagListByArticleId(articleIdList);

        // 把查询出来的 标签 添加到文章的tags属性中
        articleVoDataTransform(articleVoPageInfo.getList(), tagListByArticleId);

        return articleVoPageInfo;

    }

    /**
     * @param dto: 搜索参数
     * @return void
     * @作者: Ideal
     * @方法名: searchList
     * @说明: 关键字搜索列表
     * @时间: 2022/7/19 15:56
     */
    @Override
    public PageInfo<ArticleVo> searchList(SearchDto dto) {

        // 根据条件分页查询所有文章
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        PageInfo<ArticleVo> articleVoPageInfo = new PageInfo<>(baseMapper.searchArticleList(dto));

        // 拿到所有文章id
        List<Integer> articleIds = articleVoPageInfo.getList().stream()
                .map(ArticleVo::getId)
                .collect(Collectors.toList());

        // 根据文章id 获取文章标签
        List<QueryTagDto> tagListByArticleId = tagMapper.getTagListByArticleId(articleIds);

        // 把查询出来的 标签 添加到文章的tags属性中
        articleVoDataTransform(articleVoPageInfo.getList(), tagListByArticleId);

        return articleVoPageInfo;
    }

    /** 
    * @作者: Ideal
    * @说明: 将查询出来的标签列表赋值到ArticleVo中
    * @时间: 2022/7/29 13:02
    * @param articleVoList: 文章列表
    * @param tagDtoList: 对应文章的标签列表
    * @return void
    */
    public void articleVoDataTransform(List<ArticleVo> articleVoList, List<QueryTagDto> tagDtoList) {
        articleVoList.forEach(articleVo -> {
            tagDtoList.forEach(tagDto -> {
                if (tagDto.getArticleId().equals(articleVo.getId())) {
                    articleVo.setTags(tagDto.getTagPoList());
                }
            });
        });
    }



    /**
    * @作者: Ideal
    * @说明: 根据id查询文章详情
    * @时间: 2022/7/25 09:41
    * @param id: 文章id
    * @return com.ideal.golden.model.vo.ArticleDetailVo
    */
    public ArticleDetailVo getArticleDetailById(Integer id) {
        ArticleDetailVo articleDetailVo = new ArticleDetailVo();

        ArticlePo articlePo = baseMapper.selectById(id);
        // 文章字段
        articleDetailVo.setId(articlePo.getId())
                .setContent(articlePo.getContent())
                .setImage(articlePo.getImage())
                .setLike(articlePo.getLike())
                .setCreateTime(articlePo.getCreateTime())
                .setUpdateTime(articlePo.getUpdateTime())
                .setTitle(articlePo.getTitle())
                .setView(articlePo.getView());

        // 标签
        List<TagPo> tagPoList = articleTagMapper.selectTagListByArticleId(id);
        articleDetailVo.setTags(tagPoList);

        // 类型
        TypePo typePo = typeMapper.selectById(articlePo.getTypeId());
        articleDetailVo.setTypePo(typePo);

        // 对评论做处理
        articleDetailVo.setCommentsVos(getCommentsByArticle(id));

        return articleDetailVo;

    }

    /**
    * @作者: Ideal
    * @说明: 根据文章id获取文章下的所有评论
    * @时间: 2022/7/26 13:30
    * @param id: 文章id
    * @return java.util.List<com.ideal.golden.model.vo.CommentsVo>
    */
    public List<CommentsVo> getCommentsByArticle(Integer id) {
        List<CommentsVo> commentsVos = commentsMapper.selectListByArticleId(id);
        return commentsVos;
    }

    /**
     * @param dto: 文章dto
     * @return boolean
     * @作者: Ideal
     * @方法名: save
     * @说明: 添加文章
     * @时间: 2022/7/19 16:03
     */
    @Transactional
    @Override
    public boolean save(ArticleSaveDto dto) {
        // TODO 鉴权

        // 文章po对象
        ArticlePo articlePo = new ArticlePo()
                .setTitle(dto.getTitle())
                .setContent(dto.getContent())
                .setAAbstract(dto.getBrief())
                .setUserId(dto.getUserId())
                .setTypeId(dto.getTypeId());

        try {
            // 保存文章
            save(articlePo);

            // 更新文章与标签的映射表信息
            dto.getTags().forEach((tagId) -> {
                ArticleTagPo articleTagPo = new ArticleTagPo()
                        .setArticleId(articlePo.getId())
                        .setTagId(tagId);
                articleTagService.saveOrUpdate(articleTagPo);
            });
            return true;
        } catch (Exception e) {
            return false;
        }


    }

    /**
     * @param ids: 文章id字符串
     * @return boolean
     * @作者: Ideal
     * @方法名: removeArticle
     * @说明: 批量删除文章  并且同时删除改文章下的所有评论
     * @时间: 2022/7/19 15:57
     */
    @Override
    public void removeArticle(List<String> ids) {
        // TODO 获取token信息
        for (String id : ids) {
            // 删除文章
            baseMapper.deleteById(id);

            // 删除评论
            commentsService.removeCommentsByArticle(id);

            // 删除映射表里的内容
            articleTagService.removeByArticleId(id);
        }
    }


}
