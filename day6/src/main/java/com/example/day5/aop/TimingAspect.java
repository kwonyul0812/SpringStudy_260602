package com.example.day5.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimingAspect {

    @Around("execution(* com.example.day5.controller..*(..))")
    public Object measure(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
//        Thread.sleep(1500);
        try {
            return pjp.proceed();
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed < 1000) {
                log.info("{}ms 경과했습니다", elapsed);
            } else {
                log.warn("1초 초과했습니다. {}ms", elapsed);
            }
        }

    }
}
