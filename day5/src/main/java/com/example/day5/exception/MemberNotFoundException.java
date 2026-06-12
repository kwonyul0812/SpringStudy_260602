package com.example.day5.exception;

public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException(Long id) {
        super("MEMBER_NOT_FOUND", "회원을 찾을 수 없습니다. id=" + id);
    }
}
