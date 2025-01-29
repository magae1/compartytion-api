package com.compartytion.global.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public record ActionResponse(
    @Schema(name = "detail", title = "상세 정보", description = "알림에 쓰일 알림 메시지입니다.")
    String detail,
    @Schema(description = "상태 코드")
    int code) {

}
