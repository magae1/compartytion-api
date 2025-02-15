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
public class CompetitionCreationDTO {

  private String title;
  private String introduction;
  private Integer numOfParticipants;
  private Boolean isTeamGame;
  private Boolean isPublic;
  private Long creatorId;

}
