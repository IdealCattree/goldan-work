package com.ideal.golden.pojo.result;

import com.ideal.golden.enhance.JSONAble;

import java.util.HashMap;

/**
 * 统一返回格式
 */
public class R extends HashMap<String, Object> implements JSONAble {
    private static final String K_CODE = "code";
    private static final String K_MSG = "msg";
    private static final String K_DATA = "data";

    private static final int SUCCESS_CODE = 0;
    private static final int DEFAULT_ERROR_CODE = CodeMsg.BAD_REQUEST.getCode();

    public R setCode(int code) {
        put(K_CODE, code);
        return this;
    }

    public R setMsg(String msg) {
        put(K_MSG, msg);
        return this;
    }

    public R setData(Object data) {
        put(K_DATA, data);
        return this;
    }

    public R setSuccess(boolean success) {
        return success ? setCode(SUCCESS_CODE) : setCode(DEFAULT_ERROR_CODE);
    }
}
