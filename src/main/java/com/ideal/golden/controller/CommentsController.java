package com.ideal.golden.controller;

import com.ideal.golden.model.entity.CommentsPo;
import com.ideal.golden.model.result.ResultVo;
import com.ideal.golden.service.CommentsService;
import com.ideal.golden.common.utils.ResultHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentsService service;

    /**
    * @作者: Ideal
    * @方法名: addComments
    * @说明: 添加评论的控制层代码
    * @时间: 2022/7/19 23:51
    * @param commentsPo: 评论入参
    * @return com.ideal.golden.model.result.ResultVo
    */
    @PostMapping("/save")
    public ResultVo addComments(CommentsPo commentsPo) {

        return service.saveOrUpdate(commentsPo) ?
                ResultHelper.ok("添加成功") : ResultHelper.error("添加失败");
    }

    @DeleteMapping("/remove")
    public ResultVo removeCommentsById(String id) {
        return service.remove(id) ?
                ResultHelper.ok("删除成功") : ResultHelper.error("删除失败");
    }
}
