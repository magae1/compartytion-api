package com.compartytion.global.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public record ActionResponse(
    @Schema(description = "메시지") String message) {

}
