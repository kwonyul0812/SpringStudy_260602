package com.example.day5.exception;

// 비즈니스 예외 공통 부모
public class BusinessException extends RuntimeException {
    private final String code;
    public BusinessException(String code, String message) {
        super(message); // message 변수는 부모클래스의 private 이므로 부모 생성자를 통해 전달해서 저장해야함.
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
