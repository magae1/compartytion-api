package com.compartytion.domain.competition.dto;


import io.swagger.v3.oas.annotations.media.Schema;


public record CompetitionPermissionsResponse(
    @Schema(description = "대회 관리자 여부")
    boolean isManager,
    @Schema(description = "대회 참가자 여부")
    boolean isParticipant
) {

}
