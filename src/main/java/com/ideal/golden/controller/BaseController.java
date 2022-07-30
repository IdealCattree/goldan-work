package com.ideal.golden.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ideal.golden.model.result.ResultVo;
import com.ideal.golden.common.utils.ResultHelper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;


public abstract class BaseController<T> {
    protected abstract IService<T> getService();

    /**
     * 保存或更新
     * @param entity 实体
     * @return 统一处理的返回值
     */
    @PostMapping("/save")
    public ResultVo save(@RequestBody T entity) {
        boolean save = getService().saveOrUpdate(entity);
        return save ? ResultHelper.ok("保存成功") : ResultHelper.error("保存失败");
    }

    /**
     * 删除
     * @param ids 实体的id的字符串 "1,2,3" 多个ID用 ","隔开
     * @return 统一处理的返回值
     */
    @DeleteMapping("/remove")
    public ResultVo remove(String ids) {
        boolean b = getService().removeByIds(Arrays.asList(ids.split(",")));
        return b ? ResultHelper.ok("删除成功") : ResultHelper.error("删除失败");
    }

}
