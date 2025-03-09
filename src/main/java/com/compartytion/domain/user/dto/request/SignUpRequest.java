package com.compartytion.domain.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record SignUpRequest(
    @Schema(description = "이메일", example = "root@example.com")
    @NotBlank(message = "이메일은 반드시 입력해야 합니다.")
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    String email,
    @Schema(description = "사용자명", example = "root-user")
    @NotBlank(message = "사용자명은 반드시 입력해야 합니다.")
    String username,
    @Schema(description = "비밀번호", example = "password")
    @Size(message = "비밀번호는 최소 8자 이상이어야 합니다.", min = 8, max = 100)
    @NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
    String password,
    @Schema(description = "비밀번호(확인)", example = "password")
    @Size(message = "비밀번호는 최소 8자 이상이어야 합니다.", min = 8, max = 100)
    @NotBlank(message = "비밀번호(확인)은 반드시 입력해야 합니다.")
    String confirmedPassword
) {

}
