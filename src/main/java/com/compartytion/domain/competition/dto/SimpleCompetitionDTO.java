package com.compartytion.domain.competition.dto;


import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.user.dto.SimpleAccountDTO;


@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleCompetitionDTO {

  private Long id;
  private String title;
  private String introduction;
  private SimpleAccountDTO creator;
  private Competition.Status status;
  private LocalDateTime createdAt;
  private boolean isTeamGame;
  private boolean isPublic;
}
