package com.example.day5.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    @NotBlank(message = "이름은 필수입니다.")
    @Size(min = 2, max = 20, message = "2~20자 이내로 작성해주세요")
    private String name;

    @NotBlank
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotNull
    @Min(0)
    @Max(150)
    private Integer age;
}
