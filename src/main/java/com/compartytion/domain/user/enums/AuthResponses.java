package com.compartytion.domain.user.enums;


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
public enum AuthResponses implements ActionResponseEntityBuilder {
  EMAIL_VERIFIED(HttpStatus.OK, "이메일이 확인됐습니다."),
  PASSWORD_CHANGED(HttpStatus.OK, "비밀번호가 변경됐습니다."),
  SIGN_UP_COMPLETED(HttpStatus.CREATED, "회원가입이 완료됐습니다.");

  private final HttpStatus httpStatus;
  private final String message;

  public ResponseEntity<ActionResponse> toActionResponseEntity() {
    return new ResponseEntity<>(new ActionResponse(message, httpStatus.value()), httpStatus);
  }
}
