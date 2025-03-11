package com.compartytion.domain.application.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;


public record ApplicationCreationResponse(
    @Schema(description = "대회 접속 아이디")
    String identifier,
    @Schema(name = "detail", title = "상세 정보")
    String detail
) {

}
