package com.compartytion.domain.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record LoginRequest(
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    @NotBlank(message = "이메일은 반드시 입력해야 합니다.")
    String email,
    @NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
    String password
) {

}
