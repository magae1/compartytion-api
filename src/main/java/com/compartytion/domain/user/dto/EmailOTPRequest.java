package com.compartytion.domain.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record EmailOTPRequest(
    @Schema(description = "이메일", example = "root@example.com")
    @NotBlank(message = "이메일은 반드시 입력해야 합니다.")
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    String email,
    @Schema(description = "OTP", example = "123456")
    @NotBlank(message = "OTP는 반드시 입력해야 합니다.")
    @Size(message = "OTP는 6자입니다.", min = 6, max = 6)
    String otp
) {

}
