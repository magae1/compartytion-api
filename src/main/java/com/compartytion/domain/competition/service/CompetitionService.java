package com.compartytion.domain.competition.service;


import java.util.Arrays;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.compartytion.domain.competition.dto.CompetitionCreationDTO;
import com.compartytion.domain.competition.dto.CompetitionDeletionDTO;
import com.compartytion.domain.competition.dto.CompetitionModificationDTO;
import com.compartytion.domain.competition.dto.CompetitionPermissionsDTO;
import com.compartytion.domain.competition.dto.CompetitionStatusChangeDTO;
import com.compartytion.domain.competition.dto.response.CompetitionTitleOnlyResponse;
import com.compartytion.domain.competition.dto.SimpleCompetitionDTO;
import com.compartytion.domain.competition.mapper.CompetitionMapper;
import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.model.entity.Competition.Status;
import com.compartytion.domain.repository.CompetitionRepository;
import com.compartytion.domain.repository.ParticipantRepository;
import com.compartytion.domain.repository.projection.CompetitionStatusAndCreatorId;
import com.compartytion.domain.repository.projection.IdAndTitleOnly;
import com.compartytion.global.dto.PageResponse;

import static com.compartytion.domain.competition.enums.CompetitionExceptions.CANT_CHANGE_STATUS;
import static com.compartytion.domain.competition.enums.CompetitionExceptions.CANT_CREATE_COMPETITION;
import static com.compartytion.domain.competition.enums.CompetitionExceptions.COMPETITION_NOT_FOUND;
import static com.compartytion.domain.competition.enums.CompetitionExceptions.FAIL_MODIFICATION;
import static com.compartytion.domain.competition.enums.CompetitionExceptions.NO_CREATOR_PERMISSION;
import static com.compartytion.domain.competition.enums.CompetitionExceptions.STATUS_NOT_FOUND;


@Log4j2
@Service
@RequiredArgsConstructor
public class CompetitionService {

  private final CompetitionRepository competitionRepo;
  private final ParticipantRepository participantRepo;

  @Transactional
  @PreAuthorize("hasRole('USER')")
  public Long createCompetition(CompetitionCreationDTO creationDTO) throws ResponseStatusException {
    log.debug(creationDTO);
    Competition newCompetition = CompetitionMapper.toEntity(creationDTO);
    try {
      Competition savedCompetition = competitionRepo.save(newCompetition);
      return savedCompetition.getId();
    } catch (RuntimeException e) {
      log.error("Fail to create competition: {}", e.getMessage());
      throw CANT_CREATE_COMPETITION.toResponseStatusException();
    }
  }

  @Transactional
  @PreAuthorize("hasRole('USER')")
  public Long deleteCompetition(CompetitionDeletionDTO deletionDTO) throws ResponseStatusException {
    log.debug(deletionDTO);
    CompetitionStatusAndCreatorId competitionStatusAndCreatorId = competitionRepo.findCompetitionStatusAndCreatorIdById(
            deletionDTO.getCompetitionId())
        .orElseThrow(COMPETITION_NOT_FOUND::toResponseStatusException);
    log.debug(competitionStatusAndCreatorId);

    if (!Objects.equals(deletionDTO.getAccountId(), competitionStatusAndCreatorId.creatorId())) {
      throw NO_CREATOR_PERMISSION.toResponseStatusException();
    }

    if (competitionStatusAndCreatorId.status().equals(Status.PLAY)
        || competitionStatusAndCreatorId.status().equals(Status.DONE)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "대회(" + competitionStatusAndCreatorId.status().getMessage() + ")는 삭제할 수 없습니다.");
    }

    competitionRepo.deleteByCompetitionId(competitionStatusAndCreatorId.id());

    return deletionDTO.getCompetitionId();
  }

  @Transactional
  @PreAuthorize("hasRole('USER')")
  public Long modifyCompetition(CompetitionModificationDTO modificationDTO)
      throws ResponseStatusException {
    log.debug(modificationDTO);
    Competition competition = getCompetitionEntity(modificationDTO.getCompetitionId());
    if (!Objects.equals(modificationDTO.getAccountId(), competition.getCreator().getId())) {
      throw NO_CREATOR_PERMISSION.toResponseStatusException();
    }

    try {
      Competition savedCompetition = competitionRepo.save(
          CompetitionMapper.updateEntity(competition, modificationDTO));
      return savedCompetition.getId();
    } catch (RuntimeException e) {
      log.error("Fail to modify competition: {}", e.getMessage());
      throw FAIL_MODIFICATION.toResponseStatusException();
    }
  }

  public SimpleCompetitionDTO getSimpleCompetitionDTO(Long competitionId)
      throws ResponseStatusException {
    Competition competition = getCompetitionEntity(competitionId);
    return CompetitionMapper.toSimpleCompetitionDTO(competition);
  }

  @Transactional
  @PreAuthorize("hasRole('USER')")
  public CompetitionStatusChangeDTO changeCompetitionStatus(
      CompetitionStatusChangeDTO statusChangeDTO) throws ResponseStatusException {
    log.debug(statusChangeDTO);
    CompetitionStatusAndCreatorId competitionStatusAndCreatorId = competitionRepo.findCompetitionStatusAndCreatorIdById(
            statusChangeDTO.getCompetitionId())
        .orElseThrow(COMPETITION_NOT_FOUND::toResponseStatusException);
    log.debug(competitionStatusAndCreatorId);

    if (!Objects.equals(statusChangeDTO.getAccountId(),
        competitionStatusAndCreatorId.creatorId())) {
      throw NO_CREATOR_PERMISSION.toResponseStatusException();
    }

    Competition.Status expectedStatus = Competition.Status.getNextStatus(
            competitionStatusAndCreatorId.status())
        .orElseThrow(CANT_CHANGE_STATUS::toResponseStatusException);

    Competition.Status requestedStatus = Arrays.stream(Status.values())
        .filter((s) -> s.getMessage().equals(statusChangeDTO.getStatus()))
        .findFirst()
        .orElseThrow(STATUS_NOT_FOUND::toResponseStatusException);

    if (!expectedStatus.equals(requestedStatus)) {
      throw CANT_CHANGE_STATUS.toResponseStatusException();
    }

    competitionRepo.updateStatusById(competitionStatusAndCreatorId.id(), requestedStatus);

    return CompetitionStatusChangeDTO.builder()
        .status(requestedStatus.getMessage())
        .accountId(statusChangeDTO.getAccountId())
        .competitionId(statusChangeDTO.getCompetitionId())
        .build();
  }

  @PreAuthorize("hasRole('USER')")
  public PageResponse<CompetitionTitleOnlyResponse> getJoinedCompetitionPage(Long accountId,
      Pageable pageable) {
    Page<IdAndTitleOnly> res = competitionRepo.findJoinedCompetitionsByAccountId(accountId,
        pageable);
    return new PageResponse<>(res,
        (proj) -> new CompetitionTitleOnlyResponse(proj.getId(), proj.getTitle()));
  }

  @Transactional(readOnly = true)
  public CompetitionPermissionsDTO getCompetitionPermissions(Long competitionId, Long accountId)
      throws ResponseStatusException {
    Competition competition = getCompetitionEntity(competitionId);

    boolean isManager = Objects.equals(competition.getCreator().getId(), accountId);
    boolean isParticipant = participantRepo.existsByCompetitionIdAndAccountId(competitionId,
        accountId);
    return CompetitionPermissionsDTO.builder()
        .isManager(isManager)
        .isParticipant(isParticipant)
        .build();
  }

  private Competition getCompetitionEntity(Long competitionId) throws ResponseStatusException {
    log.debug("Find competition by ID: {}", competitionId);
    return competitionRepo.findById(competitionId)
        .orElseThrow(COMPETITION_NOT_FOUND::toResponseStatusException);
  }
}
