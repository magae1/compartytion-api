package com.compartytion.domain.competition.mapper;


import com.compartytion.domain.competition.dto.CompetitionCreationDTO;
import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.model.entity.Competition;


public class CompetitionMapper {

  public static Competition toEntity(Long competitionId, CompetitionCreationDTO creationDTO) {
    return Competition.builder()
        .id(competitionId)
        .title(creationDTO.getTitle())
        .introduction(creationDTO.getIntroduction())
        .numOfParticipants(creationDTO.getNumOfParticipants())
        .isPublic(creationDTO.getIsPublic())
        .isTeamGame(creationDTO.getIsTeamGame())
        .creator(Account.builder()
            .id(creationDTO.getCreatorId())
            .build())
        .build();
  }


}
