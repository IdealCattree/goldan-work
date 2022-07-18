package com.ideal.golden.utils;

import com.ideal.golden.exception.CommonException;
import com.ideal.golden.pojo.result.R;
import org.springframework.dao.DuplicateKeyException;

public class Rs {
    public static R ok(String msg) {
        return new R().setSuccess(true).setMsg(msg);
    }

    public static R ok(Object data) {
        return new R().setSuccess(true).setData(data);
    }

    public static R error(String msg) {
        return new R().setSuccess(false).setMsg(msg);
    }

    public static R error(Throwable t) {
        R r = new R();
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
}
