package com.compartytion.domain.competition.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;


public record CompetitionStatusChangeResponse(
    @Schema(description = "대회 상태")
    String status,
    @Schema(description = "상세 정보")
    String detail
) {

}
