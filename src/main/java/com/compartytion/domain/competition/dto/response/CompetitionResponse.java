package com.compartytion.domain.competition.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;


public record CompetitionResponse(
    @Schema(description = "생성된 대회 ID")
    Long competitionId,
    @Schema(description = "상세 정보")
    String detail
) {

}
