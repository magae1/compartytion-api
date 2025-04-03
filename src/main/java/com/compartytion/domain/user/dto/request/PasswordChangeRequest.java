package com.compartytion.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


public record PasswordChangeRequest(
    @Schema(description = "비밀번호")
    @NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
    String password,
    @Schema(description = "비밀번호(확인)")
    @NotBlank(message = "비밀번호(확인)는 반드시 입력해야 합니다.")
    String confirmedPassword
) {

}
