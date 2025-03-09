package com.compartytion.domain.competition.mapper;


import com.compartytion.domain.competition.dto.CompetitionCreationDTO;
import com.compartytion.domain.competition.dto.CompetitionModificationDTO;
import com.compartytion.domain.competition.dto.CompetitionPermissionsDTO;
import com.compartytion.domain.competition.dto.response.CompetitionPermissionsResponse;
import com.compartytion.domain.competition.dto.SimpleCompetitionDTO;
import com.compartytion.domain.competition.dto.response.SimpleCompetitionResponse;
import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.user.mapper.AccountMapper;


public class CompetitionMapper {

  public static Competition toEntity(CompetitionCreationDTO creationDTO) {
    return Competition.builder()
        .title(creationDTO.getTitle())
        .introduction(creationDTO.getIntroduction())
        .isPublic(creationDTO.getIsPublic())
        .isTeamGame(creationDTO.getIsTeamGame())
        .creator(Account.builder()
            .id(creationDTO.getCreatorId())
            .build())
        .build();
  }

  public static Competition updateEntity(Competition entity,
      CompetitionModificationDTO modificationDTO) {
    Competition.CompetitionBuilder builder = Competition.builder();
    builder.id(entity.getId());
    builder.creator(entity.getCreator());
    builder.isPublic(entity.isPublic());

    if (modificationDTO.getTitle() != null) {
      builder.title(modificationDTO.getTitle());
    } else {
      builder.title(entity.getTitle());
    }

    if (modificationDTO.getIntroduction() != null) {
      builder.introduction(modificationDTO.getIntroduction());
    } else {
      builder.introduction(entity.getIntroduction());
    }

    return builder.build();
  }

  public static SimpleCompetitionDTO toSimpleCompetitionDTO(Competition competition) {
    return SimpleCompetitionDTO.builder()
        .id(competition.getId())
        .title(competition.getTitle())
        .introduction(competition.getIntroduction())
        .createdAt(competition.getCreatedAt())
        .isPublic(competition.isPublic())
        .isTeamGame(competition.isTeamGame())
        .status(competition.getStatus())
        .creator(AccountMapper.toSimpleAccountDTO(competition.getCreator()))
        .build();
  }

  public static CompetitionPermissionsResponse toPermissionsResponse(
      CompetitionPermissionsDTO permissionsDTO) {
    return new CompetitionPermissionsResponse(permissionsDTO.isManager(),
        permissionsDTO.isParticipant());
  }

  public static SimpleCompetitionResponse toSimpleCompetitionResponse(
      SimpleCompetitionDTO simpleCompetitionDTO
  ) {
    return new SimpleCompetitionResponse(
        simpleCompetitionDTO.getId(),
        simpleCompetitionDTO.getTitle(),
        simpleCompetitionDTO.getIntroduction(),
        simpleCompetitionDTO.getCreator(),
        simpleCompetitionDTO.getStatus().getMessage(),
        simpleCompetitionDTO.getCreatedAt(),
        simpleCompetitionDTO.isTeamGame(),
        simpleCompetitionDTO.isPublic());
  }
}
