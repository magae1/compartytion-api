package com.compartytion.domain.application.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@Getter
@ToString
@RequiredArgsConstructor
public enum ApplicationExceptions {
  NOT_MATCHED_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
  CREATOR_CANT_APPLY(HttpStatus.BAD_REQUEST, "대회 개최자는 참가 신청할 수 없습니다"),
  NOT_RECRUITING(HttpStatus.BAD_REQUEST, "모집 마감한 대회입니다."),
  ALREADY_LISTED(HttpStatus.BAD_REQUEST, "이미 신청했거나 참가 중인 대회입니다.");

  private final HttpStatus httpStatus;
  private final String message;

  public ResponseStatusException toResponseStatusException() {
    return new ResponseStatusException(httpStatus, message);
  }
}
