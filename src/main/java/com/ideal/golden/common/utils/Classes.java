package com.ideal.golden.common.utils;

import java.lang.reflect.Field;

/**
 * @作者 Ideal
 * @时间 2022-07-20 17:03
 * @类名 Classes
 * @类说明 操作类的工具类
 */
public class Classes {

    /**
    * @作者: Ideal
    * @方法名: notObject
    * @说明: 返回第一个不为Object的类
    * @时间: 2022/7/20 17:12
    * @param sources: 类数组
    * @return java.lang.Class<?>
    */
    public static Class<?> notObject(Class<?> ...sources) {
        if (sources == null) return null;
        for (Class<?> source : sources) {
            if (!source.equals(Object.class)) return source;
        }
        return null;
    }

    /** 
    * @作者: Ideal
    * @方法名: getField
    * @说明: 获取cls类中的fieldName属性
    * @时间: 2022/7/20 19:44
    * @param cls: 
    * @param fieldName:
    * @return java.lang.reflect.Field
    */
    public static Field getField(Class<?> cls, String fieldName) throws Exception {
        return enumerateFields(cls, (field, curCls) -> {
            if (field.getName().equals(fieldName)) return Stop.create(field);
            return null;
        });
    }

    public static <T> T enumerateFields(Class<?> cls, FieldConsumer<T> fieldConsumer) throws Exception {
        if (fieldConsumer == null || cls == null) return null;

        Class<?> curCls = cls;
        while (!curCls.equals(Object.class)) {
            for (Field field : curCls.getDeclaredFields()) {
                Stop<T> stop = fieldConsumer.accept(field, curCls);
                if (stop != null) {
                    return stop.getData();
                }

            }
            curCls = cls.getSuperclass();
        }
        return null;

    }


    public interface FieldConsumer<T> {
        Stop<T> accept(Field field, Class<?> ownerCls) throws Exception;
    }


}
