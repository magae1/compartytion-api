package com.compartytion.domain.competition.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record CompetitionCreationRequest(
    @Schema(description = "대회명", example = "예시 대회")
    @NotBlank(message = "대회명은 반드시 입력되어야 합니다.")
    @Size(max = 150, message = "대회명은 최대 150자까지 입력 가능합니다.")
    String title,
    @Schema(description = "대회 소개", example = "어서오세요. 예시 대회입니다.")
    @NotBlank(message = "대회 소개는 반드시 입력되어야 합니다.")
    String introduction,
    // 원시 타입인 boolean일 경우 작동하지 않는다.
    @NotNull(message = "팀 게임 여부는 반드시 입력되어야 합니다.")
    @Schema(description = "팀 게임 여부", example = "false")
    Boolean isTeamGame
) {

}
