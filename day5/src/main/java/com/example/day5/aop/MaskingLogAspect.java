package com.example.day5.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class MaskingLogAspect {

    @Around("@annotation(maskLog)")
    public Object around(ProceedingJoinPoint pjp, MaskLog maskLog) throws Throwable {
        String[] fields = maskLog.fields();
        log.info("masked fields: {}", Arrays.toString(fields));
        return pjp.proceed();
    }
}
