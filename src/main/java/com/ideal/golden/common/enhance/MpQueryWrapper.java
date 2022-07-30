package com.ideal.golden.common.enhance;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * 增强LambdaQueryWrapper
 * @param <T>
 */
public class MpQueryWrapper<T> extends LambdaQueryWrapper<T> {
    /**
     * 增强like方法
     * @param value
     * @param funcs
     * @return
     */
    public MpQueryWrapper<T> like(Object value, SFunction<T, ?> ...funcs) {
        if (value == null) return this;

        if (value.toString().length() == 0) return this;

        return (MpQueryWrapper<T>) nested((w) -> {
            for (SFunction<T, ?> func : funcs) {
                w.like(func, value).or();
            }
        });
    }
}
