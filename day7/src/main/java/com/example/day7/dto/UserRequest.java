package com.example.day7.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequest {
    private String name;
    private int age;

    public UserRequest(String name, int age) {
        this.name = name;
        this.age = age;
    }


}
