package com.hjtang.basic.util;

import lombok.Data;

@Data
public class AjaxResult {
    private Boolean success = true;
    private String message = "操作成功";
    private Object resultObj;
    //1000 成功
    private String code = "1000";

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
    public AjaxResult setCode(String code){
        this.code = code;
        return this;
    }

    public AjaxResult setResultObj(Object obj){
        this.resultObj = obj;
        return this;
    }
}
