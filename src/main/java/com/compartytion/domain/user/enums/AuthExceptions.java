package com.compartytion.domain.user.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.compartytion.global.dto.ResponseStatusExceptionBuilder;


@Getter
@ToString
@RequiredArgsConstructor
public enum AuthExceptions implements ResponseStatusExceptionBuilder {
  NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없습니다.");

  private final HttpStatus httpStatus;
  private final String message;

  public ResponseStatusException toResponseStatusException() {
    return new ResponseStatusException(httpStatus, message);
  }
}
