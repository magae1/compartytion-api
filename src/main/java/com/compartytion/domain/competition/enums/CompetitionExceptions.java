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
  CANT_CREATE_COMPETITION(HttpStatus.BAD_REQUEST, "대회를 생성할 수 없습니다."),
  COMPETITION_NOT_FOUND(HttpStatus.NOT_FOUND, "대회를 찾을 수 없습니다."),
  NO_CREATOR_PERMISSION(HttpStatus.FORBIDDEN, "대회 개최자 권한이 없습니다."),
  FAIL_MODIFICATION(HttpStatus.BAD_REQUEST, "대회 수정에 실패했습니다."),
  STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없는 대회 상태입니다."),
  CANT_CHANGE_STATUS(HttpStatus.BAD_REQUEST, "대회 상태를 변경할 수 없습니다.");

  private final HttpStatus httpStatus;
  private final String message;

  public ResponseStatusException toResponseStatusException() {
    return new ResponseStatusException(httpStatus, message);
  }
}
