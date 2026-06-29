package com.example.day5.dto;

import com.example.day5.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String createAt;

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(),
                member.getName(),
                member.getEmail(),
                member.getAge(),
                member.getCreatedAt().toString());
    }
}