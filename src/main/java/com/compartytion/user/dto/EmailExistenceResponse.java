package com.compartytion.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailExistenceResponse {

  @Schema(description = "이메일", example = "root@example.com")
  private String email;

  @Schema(description = "존재 여부", example = "true")
  private boolean exists;

}
