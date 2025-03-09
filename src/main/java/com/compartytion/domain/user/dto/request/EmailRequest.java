package com.compartytion.domain.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record EmailRequest(
    @Schema(description = "이메일", example = "root@example.com")
    @NotBlank(message = "이메일은 반드시 입력해야 합니다.")
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    String email) {

}