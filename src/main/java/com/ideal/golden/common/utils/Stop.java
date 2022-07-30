package com.ideal.golden.common.utils;

import lombok.Data;

/**
 * @作者 Ideal
 * @时间 2022-07-20 15:45
 * @类名 Stop
 * @类说明
 */
@Data
public class Stop<T> {
    private T data;

    public static <T> Stop<T> create() {
        return new Stop<>();
    }

    public static <T> Stop<T> create(T data) {
        Stop<T> stop = new Stop<>();
        stop.setData(data);
        return stop;
    }
}
