package com.ideal.golden.common.utils;

import com.ideal.golden.common.exception.CommonException;
import com.ideal.golden.model.result.CodeMsg;
import com.ideal.golden.model.result.ResultVo;
import org.springframework.dao.DuplicateKeyException;

public class ResultHelper<T> {
    public static ResultVo ok(String msg) {
        return new ResultVo().setSuccess(true).setMsg(msg);
    }

    public static<T> ResultVo<T> ok(T data) {
        return new ResultVo().setSuccess(true).setData(data);
    }

    public static ResultVo error(String msg) {
        return new ResultVo().setSuccess(false).setMsg(msg);
    }

    public static ResultVo error(Throwable t) {
        ResultVo r = new ResultVo();
        if (t instanceof CommonException) {
            CommonException e = (CommonException) t;
            r.setCode(e.getCode()).setMsg(e.getMessage());
            return r;
        }

        if (t instanceof DuplicateKeyException) {
            r.setCode(400).setMsg("已有该属性，请勿重复添加");
            return r;
        }
        return error(t.getMessage());

    }

    public static ResultVo raise(String msg) throws CommonException{
        throw new CommonException(msg);
    }

    public static ResultVo raise(CodeMsg codeMsg) {

        throw new CommonException(codeMsg);
    }
}
