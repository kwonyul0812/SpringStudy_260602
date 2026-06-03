package com.example.day2.dto;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String name;
    private String email;

}
