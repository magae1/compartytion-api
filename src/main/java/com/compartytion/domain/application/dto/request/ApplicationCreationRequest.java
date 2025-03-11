package com.compartytion.domain.application.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ApplicationCreationRequest(
    @Schema(description = "대회 ID", example = "1")
    @NotNull(message = "대회 ID가 입력되지 않았습니다.")
    Long competitionId,
    @Schema(description = "비밀번호", example = "password", nullable = true)
    String password,
    @Schema(description = "비밀번호(확인)", example = "password", nullable = true)
    String confirmedPassword,
    @Schema(description = "이메일", example = "application@example.com", nullable = true)
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    String email,
    @Schema(description = "이름(공개)", example = "displayed-name")
    @NotBlank(message = "이름(공개)은 반드시 입력되어야 합니다.")
    String displayedName,
    @Schema(description = "이름(비공개)", example = "hidden-name")
    @NotBlank(message = "이름(비공개)은 반드시 입력되어야 합니다.")
    String hiddenName,
    @Schema(description = "소개", example = "hi!", nullable = true)
    String shortIntroduction
) {

}
