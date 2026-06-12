package com.example.day5.exception;

public class DuplicationEmailException extends BusinessException{
    public DuplicationEmailException(String email) {
        super("DUPLICATE_EMAIL", "이미 존재하는 이메일: " + email);
    }
}
