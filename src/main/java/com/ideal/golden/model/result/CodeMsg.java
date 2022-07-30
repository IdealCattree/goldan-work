package com.ideal.golden.model.result;

public enum CodeMsg {
    BAD_REQUEST(400, "请求错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    OPERATE_SUCCESS(0, "操作成功"),
    SAVE_SUCCESS(0, "保存成功"),
    REMOVE_SUCCESS(0, "删除成功"),

    OPERATE_ERROR(4001, "操作失败"),
    SAVE_ERROR(4002, "保存失败"),
    REMOVE_ERROR(4003, "删除失败"),

    WRONG_USERNAME(5001, "用户不存在"),
    WRONG_PASSWORD(5002, "密码错误");


    private final int code;
    private final String msg;

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
