package com.ideal.golden.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ideal.golden.pojo.result.R;
import com.ideal.golden.utils.Rs;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;


public abstract class BaseController<T> {
    protected abstract IService<T> getService();

    /**
     * 保存或更新
     * @param entity 实体
     * @return 统一处理的返回值
     */
    @PostMapping("/save")
    public R save(T entity) {
        boolean save = getService().saveOrUpdate(entity);
        return save ? Rs.ok("保存成功") : Rs.error("保存失败");
    }

    /**
     * 删除
     * @param ids 实体的id的字符串 "1,2,3" 多个ID用 ","隔开
     * @return 统一处理的返回值
     */
    @DeleteMapping("/remove")
    public R remove(String ids) {
        boolean b = getService().removeByIds(Arrays.asList(ids.split(",")));
        return b ? Rs.ok("删除成功") : Rs.error("删除失败");
    }

}
