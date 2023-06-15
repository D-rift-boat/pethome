package com.hjtang.basic.util;

public enum ErrorCode {

    //相当于 调用了其有参构造方法
    SYSTEM_ERROR("1001","系统内部异常"),
    SYSTEM_ERROR_1002("1002","系统崩溃  吓不吓人");

    //错误信息
    private String message;
    //错误码
    private String code;

    ErrorCode(String code,String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
