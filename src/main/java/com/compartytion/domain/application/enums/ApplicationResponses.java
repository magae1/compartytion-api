package com.compartytion.domain.application.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.compartytion.global.dto.ActionResponse;
import com.compartytion.global.dto.ActionResponseEntityBuilder;


@Getter
@ToString
@RequiredArgsConstructor
public enum ApplicationResponses implements ActionResponseEntityBuilder {
  APPLICATION_CREATED(HttpStatus.CREATED, "대회 참가 신청이 완료됐습니다.");

  private final HttpStatus httpStatus;
  private final String message;

  public ResponseEntity<ActionResponse> toActionResponseEntity() {
    return new ResponseEntity<>(new ActionResponse(message, httpStatus.value()), httpStatus);
  }
}
