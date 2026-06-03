package com.example.day2.service;

import org.springframework.stereotype.Service;

@Service("en")
public class EnglishGreetingService implements GreetingService {

    @Override
    public String greet(String name) {
        return String.format("Hello, %s's greeting", name);
    }
}
