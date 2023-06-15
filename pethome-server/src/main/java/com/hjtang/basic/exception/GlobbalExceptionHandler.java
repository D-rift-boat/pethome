package com.hjtang.basic.exception;

import com.hjtang.basic.util.AjaxResult;
import com.hjtang.basic.util.ErrorCode;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

//增强ResquestMapping注解的方法   自动为其环绕try、catch
@RestControllerAdvice
public class GlobbalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public AjaxResult exceptionHandler(Exception e){
        System.out.println("统一异常出现");
        e.printStackTrace();
        return AjaxResult.me().setSuccess(false)
                //返回出自定义错误码及其 错误信息
                .setMessage(ErrorCode.SYSTEM_ERROR.getMessage())
                .setCode(ErrorCode.SYSTEM_ERROR.getCode());
    }

//    @ExceptionHandler(BindException.class)
//    public AjaxResult exceptionHandler1(BindException e){
//        System.out.println("自定义手机号校验注解 导致异常");
//        e.printStackTrace();
//        return AjaxResult.me().setSuccess(false)
//                .setMessage(ErrorCode.SYSTEM_ERROR_1002.getMessage())
//                .setCode(ErrorCode.SYSTEM_ERROR_1002.getCode());
//    }

    //捕获异常
    @ExceptionHandler(ConstraintViolationException.class)
    public AjaxResult constraintViolationException(ConstraintViolationException e) {
        //方法体相当于catch 代码块
        System.out.println("Spring注解验证异常");
        e.printStackTrace();
        System.out.println(e.getLocalizedMessage());
        ConstraintViolationImpl cv = (ConstraintViolationImpl)e.getConstraintViolations().toArray()[0];
        return AjaxResult.me().setSuccess(false)
                .setMessage(cv.getMessageTemplate())
                .setCode(ErrorCode.SYSTEM_ERROR_1002.getCode());
    }

}
