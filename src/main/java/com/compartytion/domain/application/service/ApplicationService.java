package com.compartytion.domain.application.service;


import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.compartytion.domain.application.dto.ApplicationCreationDTO;
import com.compartytion.domain.application.dto.UnAuthApplicationCreationDTO;
import com.compartytion.domain.application.mapper.ApplicationMapper;
import com.compartytion.domain.model.entity.Application;
import com.compartytion.domain.model.entity.Competition.Status;
import com.compartytion.domain.repository.ApplicationRepository;
import com.compartytion.domain.repository.CompetitionRepository;
import com.compartytion.domain.repository.projection.CompetitionStatusAndCreatorId;
import com.compartytion.global.component.CharAndNumberIdGenerator;
import com.compartytion.global.component.PasswordValidator;
import com.compartytion.global.exception.InvalidFormException;

import static com.compartytion.domain.application.enums.ApplicationExceptions.ALREADY_LISTED;
import static com.compartytion.domain.application.enums.ApplicationExceptions.CREATOR_CANT_APPLY;
import static com.compartytion.domain.application.enums.ApplicationExceptions.NOT_RECRUITING;
import static com.compartytion.domain.competition.enums.CompetitionExceptions.COMPETITION_NOT_FOUND;


@Log4j2
@Service
@RequiredArgsConstructor
public class ApplicationService {

  private final ApplicationRepository applicationRepo;
  private final CompetitionRepository competitionRepo;
  private final PasswordEncoder passwordEncoder;
  private final CharAndNumberIdGenerator charAndNumberIdGenerator;

  @Transactional
  public void addApplication(ApplicationCreationDTO creationDTO)
      throws ResponseStatusException, InvalidFormException {
    log.debug(creationDTO);

    CompetitionStatusAndCreatorId statusAndCreatorId = getCompetitionStatusAndCreatorId(
        creationDTO.getCompetitionId());

    if (!statusAndCreatorId.status().equals(Status.RECRUIT)) {
      throw NOT_RECRUITING.toResponseStatusException();
    }
    if (statusAndCreatorId.creatorId().equals(creationDTO.getAccountId())) {
      throw CREATOR_CANT_APPLY.toResponseStatusException();
    }
    if (isAccountIdDuplicated(creationDTO.getCompetitionId(), creationDTO.getAccountId())) {
      throw ALREADY_LISTED.toResponseStatusException();
    }

    verifyEmail(creationDTO.getCompetitionId(), creationDTO.getEmail());

    String identifier = getUniqueIdentifier(creationDTO.getCompetitionId());
    saveNewApplication(ApplicationMapper.toEntity(creationDTO, identifier));
  }


  @Transactional
  public void addApplication(UnAuthApplicationCreationDTO creationDTO)
      throws ResponseStatusException, InvalidFormException {
    log.debug(creationDTO);
    PasswordValidator.validate(creationDTO.getPassword(), creationDTO.getConfirmedPassword());

    CompetitionStatusAndCreatorId statusAndCreatorId = getCompetitionStatusAndCreatorId(
        creationDTO.getCompetitionId());

    if (!statusAndCreatorId.status().equals(Status.RECRUIT)) {
      throw NOT_RECRUITING.toResponseStatusException();
    }
    verifyEmail(creationDTO.getCompetitionId(), creationDTO.getEmail());

    String identifier = getUniqueIdentifier(creationDTO.getCompetitionId());
    saveNewApplication(ApplicationMapper.toEntity(creationDTO, identifier, passwordEncoder));
  }

  private boolean isAccountIdDuplicated(Long competitionId, Long accountId) {
    return competitionRepo.existsInParticipantAndApplicationsByIdAndAccountId(competitionId,
        accountId);
  }

  private void verifyEmail(Long competitionId, String email) throws InvalidFormException {
    log.debug("verify email: {} in competition {}", email, competitionId);
    if (competitionRepo.existsInParticipantAndApplicationsByIdAndEmail(competitionId, email)) {
      Map<String, List<String>> msgMap = Map.of("email", List.of("이미 사용 중인 이메일입니다."));
      throw new InvalidFormException(msgMap);
    }
  }

  private CompetitionStatusAndCreatorId getCompetitionStatusAndCreatorId(Long competitionId) {
    return competitionRepo.findCompetitionStatusAndCreatorIdById(
            competitionId)
        .orElseThrow(COMPETITION_NOT_FOUND::toResponseStatusException);
  }

  private String getUniqueIdentifier(Long competitionId) {
    Set<String> identifierSet = applicationRepo.findAllIdentifierInParticipantsAndApplicants(
        competitionId);

    String identifier;
    do {
      identifier = charAndNumberIdGenerator.next();
    } while (identifierSet.contains(identifier));
    log.debug("ID: {}", identifier);
    return identifier;
  }

  private void saveNewApplication(Application application) {
    applicationRepo.save(application);
    log.info("New application saved: {}", application.getIdentifier());
  }
}
