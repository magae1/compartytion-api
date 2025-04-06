package com.compartytion.domain.user.enums;


import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import com.compartytion.global.exception.InvalidFormException;


@Getter
@ToString
@RequiredArgsConstructor
public enum AuthFormExceptions {
  DUPLICATED_EMAIL("email", List.of("중복된 이메일입니다.")),
  NOT_FOUND_UNAUTHENTICATED_EMAIL("email", List.of("인증 요청을 하지 않은 이메일입니다.")),
  NOT_FOUND_FORGIVEN_PASSWORD("email", List.of("비밀번호 변경 요청을 하지 않은 이메일입니다.")),
  NOT_MATCHED_PASSWORD("password", List.of("비밀번호가 일치하지 않습니다.")),
  WRONG_OTP("otp", List.of("잘못된 OTP입니다.")),
  NOT_FOUND_EMAIL("email", List.of("이메일을 찾을 수 없습니다.")),
  ALREADY_VERIFIED_EMAIL("email", List.of("이미 확인된 이메일입니다.")),
  NOT_VERIFIED_EMAIL("email", List.of("인증이 완료되지 않은 이메일입니다."));

  private final String attributeName;
  private final List<String> messageList;

  public InvalidFormException toInvalidFormException() {
    return new InvalidFormException(this.attributeName, this.messageList);
  }
}
