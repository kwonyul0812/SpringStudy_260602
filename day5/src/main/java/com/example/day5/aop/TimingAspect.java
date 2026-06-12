package com.example.day5.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // Aspect : 공통 코드 모음 모듈
@Component
@Slf4j
public class TimingAspect {

    // @Around : 실제 메소드 실행 전과 후 적용하겠다
    // Pointcut : execution(* com.example.day5.controller..*(..)) 실제 적용 대상 주소
    // execution([접근제어자] 리턴타입 [패키지/클래스].메서드이름(파라미터) [예외])
    @Around("execution(* com.example.day5.controller..*(..))")
    public Object measure(ProceedingJoinPoint pjp) throws Throwable { // JoinPoint 원래 메소드 호출 순간.
        // 메소드 시작과 끝까지가 Advice
        long start = System.currentTimeMillis();
        String method = pjp.getSignature().toShortString();
        try {
            return pjp.proceed(); // 실제 호출된 메소드 실행
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            log.info("{} took {}ms", method, elapsed);
        }
    }
}
