package com.compartytion.domain.user.dto;


import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;


public record DetailAccountResponse(
    @Schema(description = "사용자명", example = "test-user")
    String username,
    @Schema(description = "이메일", example = "test@example.com")
    String email,
    @Schema(description = "프로필 이미지 URL", example = "https://image.com")
    String avatar,
    @Schema(description = "계정 생성일")
    LocalDateTime createdAt,
    @Schema(description = "마지막 비밀번호 변경일")
    LocalDateTime lastPasswordChangedAt
) {

}
