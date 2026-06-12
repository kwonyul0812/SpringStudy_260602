package com.example.day3.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member { // 엔티티
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDateTime createAt;
}
