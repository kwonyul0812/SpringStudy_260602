package com.example.day5.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    @NotBlank
    @Email
    private String email;
}
