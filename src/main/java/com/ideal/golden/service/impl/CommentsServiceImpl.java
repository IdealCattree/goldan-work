package com.ideal.golden.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ideal.golden.common.enhance.MpQueryWrapper;
import com.ideal.golden.mapper.CommentsMapper;
import com.ideal.golden.model.entity.CommentsPo;
import com.ideal.golden.service.CommentsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        LambdaQueryChainWrapper<CommentsPo> wrapper = lambdaQuery()
                .eq(CommentsPo::getArticleId, articleId)
                .eq(CommentsPo::getDeleteTime, null);
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
        try {
            // 从token中获取userid

            CommentsPo commentsPo = baseMapper.selectById(id);
            // TODO
            // 判断 userId 是否与 CommentsPo中的userId相同

            // 如果相同则逻辑删除
            lambdaUpdate().eq(CommentsPo::getId, id)
                    .isNull(CommentsPo::getDeleteTime)
                    .set(CommentsPo::getDeleteTime, new Date())
                    .update();

            // 判断删除的这个评论是否是顶级id
            if (commentsPo.getTopId().equals(0)) {

                // 如果是顶级id 则将其下所有评论全部逻辑删除
                lambdaUpdate().eq(CommentsPo::getTopId, id)
                        .isNull(CommentsPo::getDeleteTime)
                        .set(CommentsPo::getDeleteTime, new Date())
                        .update();

            } else { // 不是顶级评论
                // 查询该评论下的所有子评论
                List<Integer> allCommentsChild = getAllCommentsChild(id);

                // 如果有 则全部逻辑删除
                if (allCommentsChild.size() > 0) {
                    lambdaUpdate().in(CommentsPo::getId, allCommentsChild)
                            .isNull(CommentsPo::getDeleteTime)
                            .set(CommentsPo::getDeleteTime, new Date())
                            .update();
                }
            }
            return true;
        }catch (Exception e) {
            return false;
        }


    }

    /**
    * @作者: Ideal
    * @说明: 根据评论id递归查询该评论的所有子评论的id 保存到delIds中
    * @时间: 2022/7/30 17:07
    * @param commentsId:
    * @return java.util.List<java.lang.Integer>
    */
    public List<Integer> getAllCommentsChild(String commentsId) {
        MpQueryWrapper<CommentsPo> wrapper = new MpQueryWrapper<>();
        wrapper.eq(CommentsPo::getParentId, commentsId);
        List<CommentsPo> commentsPoList = baseMapper.selectList(wrapper);
        List<Integer> commentsIds = new ArrayList<>();

        // 如果查询出来有子评论，就递归查询自评论的自评论
        if (commentsPoList.size() > 0) {
            commentsIds = commentsPoList.stream()
                    .map(CommentsPo::getId)
                    .collect(Collectors.toList());
            deepCommentsTree(commentsIds);
        }

        return commentsIds;
    }

    /**
    * @作者: Ideal
    * @说明: 递归遍历评论下的所有子评论 并保存到 commentsIds 中
    * @时间: 2022/7/30 17:58
    * @param commentsIds: 评论的id列表
    * @return void
    */
    public void deepCommentsTree(List<Integer> commentsIds) {
        MpQueryWrapper<CommentsPo> wrapper = new MpQueryWrapper<>();
        wrapper.in(CommentsPo::getParentId, commentsIds);
        List<CommentsPo> commentsPoList = baseMapper.selectList(wrapper);

        if (commentsPoList.size() > 0) {
            List<Integer> newCommentsIds = commentsPoList.stream()
                    .map(CommentsPo::getId)
                    .collect(Collectors.toList());
            // 将查询出来的 ids 保存到 commentsIds中
            commentsIds.addAll(newCommentsIds);

            deepCommentsTree(newCommentsIds);
        }

    }
}
