package com.example.day5.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 모든 컨트롤러의 예외를 공통으로 처리하겠다
@Slf4j
public class GlobalExceptionHandler {

    // @ExceptionHandler : 특정 예외와 처리 메서드를 연결하는 표시
    @ExceptionHandler(MemberNotFoundException.class) // MemberNotFoundException 예외는 아래의 메소드로 처리하겠다.
    public ResponseEntity<ErrorResponse> notFound(BusinessException e) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ErrorResponse.of(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> business(BusinessException e) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException e) {
        FieldError err = e.getBindingResult().getFieldError(); // 검증한 객체 -> 검증 실패한 필드 1개
        String msg = err.getField() + ": " + err.getDefaultMessage(); // 검증 실패한 필드 이름 + ": " + 실패 메시지
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of("VALIDATION", msg));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unhandled(Exception e) {
        log.error("Unhandled exception", e); // 로그를 남기고 e 예외 객체를 넘겨줘 오류 추적가능.
        return ResponseEntity.status(500).body(ErrorResponse.of("INTERNAL", "서버 오류가 발생했습니다."));
    }
}
