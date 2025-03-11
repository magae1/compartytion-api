package com.compartytion.domain.application.service;


import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import com.compartytion.domain.application.dto.ApplicationCreationDTO;
import com.compartytion.domain.application.dto.ApplicationPasswordDTO;
import com.compartytion.domain.application.mapper.ApplicationMapper;
import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.model.entity.Application;
import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.model.entity.Competition.Status;
import com.compartytion.domain.repository.ApplicationRepository;
import com.compartytion.domain.repository.CompetitionRepository;
import com.compartytion.domain.repository.projection.CompetitionStatusAndCreatorId;
import com.compartytion.global.component.CharAndNumberIdGenerator;

import static com.compartytion.domain.application.enums.ApplicationExceptions.ALREADY_LISTED;
import static com.compartytion.domain.application.enums.ApplicationExceptions.CREATOR_CANT_APPLY;
import static com.compartytion.domain.application.enums.ApplicationExceptions.NOT_MATCHED_PASSWORD;
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
  private final SpringValidatorAdapter adapter;

  @Transactional
  public String addApplication(ApplicationCreationDTO creationDTO)
      throws ResponseStatusException, MethodArgumentNotValidException {
    log.debug(creationDTO);
    CompetitionStatusAndCreatorId shortCompetitionInfo = competitionRepo.findCompetitionStatusAndCreatorIdById(
            creationDTO.getCompetitionId())
        .orElseThrow(COMPETITION_NOT_FOUND::toResponseStatusException);

    if (!shortCompetitionInfo.status().equals(Status.RECRUIT)) {
      throw NOT_RECRUITING.toResponseStatusException();
    }

    if (!creationDTO.isAuthenticated()) {
      verifyPassword(creationDTO);
    } else {
      if (shortCompetitionInfo.creatorId().equals(creationDTO.getAccountId())) {
        throw CREATOR_CANT_APPLY.toResponseStatusException();
      }
      if (isAccountIdDuplicated(creationDTO.getCompetitionId(), creationDTO.getAccountId())) {
        throw ALREADY_LISTED.toResponseStatusException();
      }
    }

    Set<String> identifierSet = applicationRepo.findAllIdentifierInParticipantsAndApplicants(
        creationDTO.getCompetitionId());

    String identifier;
    do {
      identifier = charAndNumberIdGenerator.next();
    } while (identifierSet.contains(identifier));

    Application application = createApplication(identifier, creationDTO);
    applicationRepo.save(application);
    log.info("Application saved: {}", identifier);
    return identifier;
  }

  private boolean isAccountIdDuplicated(Long competitionId, Long accountId) {
    return competitionRepo.existsInParticipantAndApplicationsByIdAndAccountId(competitionId,
        accountId);
  }

  private void verifyPassword(ApplicationCreationDTO creationDTO)
      throws MethodArgumentNotValidException, ResponseStatusException {
    ApplicationPasswordDTO passwordDTO = ApplicationMapper.toPasswordDTO(creationDTO);
    BeanPropertyBindingResult result = new BeanPropertyBindingResult(passwordDTO, "password");
    log.debug(result);
    adapter.validate(passwordDTO, result);
    if (result.hasErrors()) {
      try {
        throw new MethodArgumentNotValidException(new MethodParameter(
            this.getClass().getDeclaredMethod("verifyPassword", ApplicationCreationDTO.class), 0),
            result);
      } catch (NoSuchMethodException e) {
        log.error("Can't get declared method: {}", e.getMessage());
      }
    } else {
      if (!passwordDTO.getPassword().equals(passwordDTO.getConfirmedPassword())) {
        throw NOT_MATCHED_PASSWORD.toResponseStatusException();
      }
    }
  }

  private Application createApplication(String identifier, ApplicationCreationDTO creationDTO) {
    Competition competition = Competition.builder().id(creationDTO.getCompetitionId()).build();

    Application.ApplicationBuilder builder = Application.builder()
        .competition(competition)
        .identifier(identifier)
        .email(creationDTO.getEmail())
        .displayedName(creationDTO.getDisplayedName())
        .hiddenName(creationDTO.getHiddenName())
        .shortIntroduction(creationDTO.getShortIntroduction());
    if (creationDTO.isAuthenticated()) {
      Account participantAccount = Account.builder().id(creationDTO.getAccountId()).build();
      builder.account(participantAccount);
    } else {
      builder.password(passwordEncoder.encode(creationDTO.getPassword()));
    }
    return builder.build();
  }
}
