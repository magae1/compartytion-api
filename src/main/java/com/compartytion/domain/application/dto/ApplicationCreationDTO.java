package com.compartytion.domain.application.dto;


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
public class ApplicationCreationDTO {

  private Long competitionId;
  private Long accountId;
  private String password;
  private String confirmedPassword;
  private String email;
  private String displayedName;
  private String hiddenName;
  private String shortIntroduction;
  @Builder.Default
  private boolean isAuthenticated = false;
}