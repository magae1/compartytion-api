package com.compartytion.domain.competition.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.compartytion.global.dto.ResponseStatusExceptionBuilder;


@Getter
@ToString
@RequiredArgsConstructor
public enum CompetitionExceptions implements ResponseStatusExceptionBuilder {
  CANT_CREATE_COMPETITION(HttpStatus.BAD_REQUEST, "대회를 생성할 수 없습니다.");

  private final HttpStatus httpStatus;
  private final String message;

  public ResponseStatusException toResponseStatusException() {
    return new ResponseStatusException(httpStatus, message);
  }
}
