package com.example.day3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
