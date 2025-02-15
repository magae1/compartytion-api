package com.compartytion.domain.competition.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record CompetitionCreationRequest(
    @Schema(description = "대회명", example = "예시 대회")
    @NotBlank(message = "대회명은 반드시 입력되어야 합니다.")
    @Size(max = 150, message = "대회명은 최대 150자까자 입력 가능합니다.")
    String title,
    @Schema(description = "대회 소개", example = "어서오세요. 예시 대회입니다.")
    @NotBlank(message = "대회 소개는 반드시 입력되어야 합니다.")
    String introduction,
    @Schema(description = "참가자 수", example = "16")
    @NotBlank(message = "참가자 수는 반드시 입력되어야 합니다.")
    @Min(value = 2, message = "참가자 수는 최소 2명이상 설정할 수 있습니다.")
    @Max(value = 64, message = "참가자 수는 최대 64명까지 설정할 수 있습니다.")
    Integer numOfParticipants,
    // 원시 타입인 boolean일 경우 작동하지 않는다.
    @NotNull(message = "팀 게임 여부는 반드시 입력되어야 합니다.")
    @Schema(description = "팀 게임 여부", example = "false")
    Boolean isTeamGame,
    @Schema(description = "공개 여부", example = "false")
    @NotNull(message = "대회 공개 여부는 반드시 입력되어야 합니다.")
    Boolean isPublic
) {

}
