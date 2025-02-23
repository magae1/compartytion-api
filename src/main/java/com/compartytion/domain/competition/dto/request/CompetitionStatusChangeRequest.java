package com.compartytion.domain.competition.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


public record CompetitionStatusChangeRequest(
    @Schema(description = "진행중")
    @NotBlank(message = "대회 상태는 반드시 입력되어야 합니다.")
    String status
) {

}
