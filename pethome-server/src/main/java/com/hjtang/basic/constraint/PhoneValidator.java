package com.hjtang.basic.constraint;

import com.hjtang.basic.annotation.Phone;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private static final String PHONE_REGEX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0-9]))\\d{8}$";

    @Override
    public void initialize(Phone constraintAnnotation) {
        //初始化
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        //对值进行手机号正则判断
        Pattern p = Pattern.compile(PHONE_REGEX);
        Matcher m = p.matcher(value);
        return m.matches();
    }
}