package com.compartytion.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record PasswordChangeRequest(
    @Schema(description = "새 비밀번호")
    @NotBlank(message = "새 비밀번호는 반드시 입력해야 합니다.")
    @Size(message = "비밀번호는 최소 8자 이상이어야 합니다.", min = 8, max = 100)
    String newPassword,
    @Schema(description = "새 비밀번호(확인)")
    @NotBlank(message = "새 비밀번호(확인)는 반드시 입력해야 합니다.")
    @Size(message = "비밀번호는 최소 8자 이상이어야 합니다.", min = 8, max = 100)
    String newConfirmedPassword
) {

}
