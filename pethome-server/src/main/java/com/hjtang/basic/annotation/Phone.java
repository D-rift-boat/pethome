package com.hjtang.basic.annotation;

import com.hjtang.basic.constraint.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//指定校验规则器
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {
    //SpringValidator必要的三个属性
    String  message() default "无效电话格式";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
