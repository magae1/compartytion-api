package com.compartytion.domain.user.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;


public record SimpleAccountResponse(
    @Schema(description = "사용자명", example = "test-user")
    String username,
    @Schema(description = "이메일", example = "test@example.com")
    String email,
    @Schema(description = "프로필 이미지 URL", example = "https://image.com")
    String avatar
) {

}
