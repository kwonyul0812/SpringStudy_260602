package com.example.day5.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 어디에 붙일것인지
@Retention(RetentionPolicy.RUNTIME) // 어노테이션 생명주기 런타임동안에도 유지
public @interface MaskLog {
    String[] fields() default {};
}
