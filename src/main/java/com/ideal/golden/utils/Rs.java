package com.ideal.golden.utils;

import com.ideal.golden.exception.CommonException;
import com.ideal.golden.pojo.result.R;

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
        return error(t.getMessage());

    }
}
