package com.compartytion.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailExistenceRequest {

  @Schema(description = "이메일", example = "root@example.com")
  @NotBlank
  @NotNull
  @Email(message = "올바르지 않은 이메일 형식입니다.")
  private String email;

}
