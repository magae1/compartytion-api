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
public class UnAuthApplicationCreationDTO {

  private Long competitionId;
  private String email;
  private String password;
  private String confirmedPassword;
  private String name;
  private String shortIntroduction;
}
