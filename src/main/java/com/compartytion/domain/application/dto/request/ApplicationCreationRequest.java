package com.compartytion.domain.application.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record ApplicationCreationRequest(
    @NotNull(message = "잘못된 대회 ID입니다.")
    @Schema(description = "대회 ID", example = "1")
    Long competitionId,
    @Schema(description = "비밀번호", example = "passWord1", nullable = true)
    String password,
    @Schema(description = "비밀번호(확인)", example = "passWord1", nullable = true)
    String confirmedPassword,
    @NotNull(message = "이메일은 반드시 입력해야 합니다.")
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    @Schema(description = "이메일", example = "application@example.com")
    String email,
    @NotBlank(message = "이름은 반드시 입력해야 합니다.")
    @Size(max = 150, message = "이름은 최대 150까지 입력 가능합니다.")
    @Schema(description = "이름", example = "test-name")
    String name,
    @Schema(description = "소개", example = "hi!", nullable = true)
    String shortIntroduction
) {

}
