package com.compartytion.domain.user.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.compartytion.global.dto.ActionResponse;


@Getter
@ToString
@RequiredArgsConstructor
public enum AuthResponses {
  OTP_SENT(HttpStatus.OK, "OTP가 전송됐습니다."),
  SIGN_UP_COMPLETED(HttpStatus.CREATED, "회원가입이 완료됐습니다.");

  private final HttpStatus httpStatus;
  private final String message;

  public ResponseEntity<ActionResponse> toActionResponseEntity() {
    return new ResponseEntity<>(new ActionResponse(message), httpStatus);
  }
}
