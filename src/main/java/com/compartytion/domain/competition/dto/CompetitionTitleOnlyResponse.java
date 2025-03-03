package com.compartytion.domain.competition.dto;


import io.swagger.v3.oas.annotations.media.Schema;


public record CompetitionTitleOnlyResponse(
    @Schema(description = "대회 ID")
    Long id,
    @Schema(description = "대회명")
    String title
) {

}
