package com.compartytion.domain.competition.dto;


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
public class CompetitionPermissionsDTO {

  @Builder.Default
  private boolean isManager = false;

  @Builder.Default
  private boolean isParticipant = false;
}
