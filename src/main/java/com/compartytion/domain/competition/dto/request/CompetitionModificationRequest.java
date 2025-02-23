package com.compartytion.domain.competition.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;


public record CompetitionModificationRequest(
    @Schema(description = "대회명", example = "예시 대회1")
    @Size(max = 150, message = "대회명은 최대 150자까지 입력 가능합니다.")
    String title,
    @Schema(description = "대회 소개", example = "안녕하세요. 예시 대회1입니다.")
    String introduction
) {

}
