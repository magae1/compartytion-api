package com.compartytion.domain.competition.enums;


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
public enum CompetitionResponses implements ActionResponseEntityBuilder {
  COMPETITION_CREATED(HttpStatus.OK, "대회가 생성됐습니다.");

  private final HttpStatus httpStatus;
  private final String message;

  public ResponseEntity<ActionResponse> toActionResponseEntity() {
    return new ResponseEntity<>(new ActionResponse(message, httpStatus.value()), httpStatus);
  }
}
