package com.ideal.golden.model.result;

import com.ideal.golden.common.enhance.JSONAble;
import lombok.Data;

import java.util.HashMap;

/**
 * 统一返回格式
 */
@Data
public class ResultVo <T> extends HashMap<String, Object> implements JSONAble {
    private static final String K_CODE = "code";
    private static final String K_MSG = "msg";
    private static final String K_DATA = "data";

    private static final int SUCCESS_CODE = 0;
    private static final int DEFAULT_ERROR_CODE = CodeMsg.BAD_REQUEST.getCode();

    public ResultVo setCode(int code) {
        put(K_CODE, code);
        return this;
    }

    public ResultVo setMsg(String msg) {
        put(K_MSG, msg);
        return this;
    }

    public ResultVo setData(T data) {
        put(K_DATA, data);
        return this;
    }

    public ResultVo setSuccess(boolean success) {
        return success ? setCode(SUCCESS_CODE) : setCode(DEFAULT_ERROR_CODE);
    }
}
