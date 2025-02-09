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
  DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
  NOT_FOUND_UNAUTHENTICATED_EMAIL(HttpStatus.BAD_REQUEST, "인증 요청을 하지 않은 이메일입니다."),
  NOT_FOUND_FORGIVEN_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호 변경 요청을 하지 않은 이메일입니다."),
  NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없습니다."),
  NOT_MATCHED_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
  WRONG_OTP(HttpStatus.BAD_REQUEST, "잘못된 OTP입니다."),
  ALREADY_VERIFIED_EMAIL(HttpStatus.BAD_REQUEST, "이미 확인된 이메일입니다."),
  NOT_VERIFIED_EMAIL(HttpStatus.BAD_REQUEST, "인증이 완료되지 않은 이메일입니다.");

  private final HttpStatus httpStatus;
  private final String message;

  public ResponseStatusException toResponseStatusException() {
    return new ResponseStatusException(httpStatus, message);
  }
}
