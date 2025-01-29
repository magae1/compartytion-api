package com.compartytion.domain.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;


public record EmailExistenceResponse(
    @Schema(description = "이메일", example = "root@example.com") String email,
    @Schema(description = "존재 여부", example = "true") boolean exists) {

}
