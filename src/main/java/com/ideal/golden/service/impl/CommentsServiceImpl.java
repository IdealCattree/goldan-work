package com.ideal.golden.service.impl;

import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ideal.golden.common.enhance.MpQueryWrapper;
import com.ideal.golden.mapper.CommentsMapper;
import com.ideal.golden.model.entity.CommentsPo;
import com.ideal.golden.service.CommentsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, CommentsPo> implements CommentsService {

    /**
    * @作者: Ideal
    * @方法名: save
    * @说明: 保存评论的service逻辑
    * @时间: 2022/7/19 23:48
    * @param commentsPo: 评论的入参数
    * @return boolean
    */
    @Override
    public boolean save(CommentsPo commentsPo) {
        // TODO
        // 从token中获取userId 并添加到实体中
        Integer userId = commentsPo.getUserId();
        if (userId == null) {
            // 如果没有token 或者token过期了 则判断 commentsPo中有没有 邮箱 和 游客名称
            if (commentsPo.getTName() == null || commentsPo.getTEmail() == null) return false;
        }

        saveOrUpdate(commentsPo);
        return true;
    }

    /**
    * @作者: Ideal
    * @说明: 根据articleId删除评论
    * @时间: 2022/7/29 13:25
    * @param articleId: 文章id
    * @return boolean
    */
    public boolean removeCommentsByArticle(String articleId) {
        MpQueryWrapper<CommentsPo> wrapper = new MpQueryWrapper<>();
        wrapper.eq(CommentsPo::getArticleId, articleId);
        int delete = baseMapper.delete(wrapper);
        return delete > 0;
    }

    /**
    * @作者: Ideal
    * @方法名: remove
    * @说明: 删除评论的业务
    * @时间: 2022/7/19 23:55
    * @param id: 评论id
    * @return boolean
    */
    @Override
    public boolean remove(String id) {
        // 从token中获取userid

        CommentsPo commentsPo = baseMapper.selectById(id);
        // TODO
        // 判断 userId 是否与 CommentsPo中的userId相同
        // 如果相同则删除
        baseMapper.deleteById(id);
        // 判断删除的这个评论是否是顶级id 如果是顶级id 则将其下所有评论全部删除
        if (commentsPo.getTopId().equals(0)) {
            MpQueryWrapper<CommentsPo> wrapper = new MpQueryWrapper<>();
            wrapper.eq(CommentsPo::getTopId, commentsPo.getId());
            baseMapper.delete(wrapper);

        }

        return false;
    }
}
