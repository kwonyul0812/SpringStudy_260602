package com.example.day5.dto;

import com.example.day5.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {
    @NotBlank
    @Size(min = 0, max = 20)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Min(0)
    @Max(150)
    private Integer age;

    public Member toEntity() {
        return new Member().builder()
                .name(name)
                .email(email)
                .age(age)
                .build();
    }
}
