package com.hjtang.basic.util;

import lombok.Data;

@Data
public class AjaxResult {
    private Boolean success = true;
    private String message = "操作成功";
    private Object resultObj;

    public static AjaxResult me() {
        return new AjaxResult();
    }

    public AjaxResult setSuccess(Boolean success){
        this.success = success;
        return this;
    }

    public AjaxResult setMessage(String message){
        this.message = message;
        return this;
    }
}
