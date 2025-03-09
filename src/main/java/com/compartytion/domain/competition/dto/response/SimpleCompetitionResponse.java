package com.compartytion.domain.competition.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

import com.compartytion.domain.user.dto.SimpleAccountDTO;


public record SimpleCompetitionResponse(
    @Schema(description = "대회 ID")
    Long id,
    @Schema(description = "대회명")
    String title,
    @Schema(description = "대회 소개")
    String introduction,
    @Schema(description = "대회 개최자 정보")
    SimpleAccountDTO creator,
    @Schema(description = "대회 상태")
    String status,
    @Schema(description = "대회 생성일")
    LocalDateTime createdAt,
    @Schema(description = "팀 대회 여부")
    boolean isTeamGame,
    @Schema(description = "대회 공개 여부")
    boolean isPublic
) {

}
