package com.compartytion.domain.application.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationPasswordDTO {

  @Size(message = "비밀번호는 최소 8자 이상이어야 합니다.", min = 8, max = 100)
  @NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
  private String password;
  @Size(message = "비밀번호는 최소 8자 이상이어야 합니다.", min = 8, max = 100)
  @NotBlank(message = "비밀번호(확인)은 반드시 입력해야 합니다.")
  private String confirmedPassword;
}
