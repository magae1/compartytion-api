package com.compartytion.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public record EmailOTPResponse(
    @Schema(description = "이메일", example = "root@example.com")
    String email,
    @Schema(description = "OTP 남은 시간", example = "900")
    long seconds
) {

}
