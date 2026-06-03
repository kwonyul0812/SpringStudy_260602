package com.example.day2.service;

import org.springframework.stereotype.Service;

@Service("ko")
public class KoreanGreetingService implements GreetingService {
    @Override
    public String greet(String name) {
        return String.format("안녕하세요. %s 인사 드립니다.", name);
    }
}
