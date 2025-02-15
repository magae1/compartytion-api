package com.compartytion.domain.competition.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.compartytion.domain.competition.dto.CompetitionCreationDTO;
import com.compartytion.domain.competition.mapper.CompetitionMapper;
import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.repository.CompetitionRepository;
import com.compartytion.global.component.Snowflake;

import static com.compartytion.domain.competition.enums.CompetitionExceptions.CANT_CREATE_COMPETITION;


@Log4j2
@Service
@RequiredArgsConstructor
public class CompetitionService {

  private final CompetitionRepository competitionRepo;
  private final Snowflake snowflake;

  public Long createCompetition(CompetitionCreationDTO creationDTO) throws ResponseStatusException {
    Competition newCompetition = CompetitionMapper.toEntity(snowflake.nextId(), creationDTO);
    try {
      Competition savedCompetition = competitionRepo.save(newCompetition);
      return savedCompetition.getId();
    } catch (IllegalArgumentException | OptimisticEntityLockException e) {
      throw CANT_CREATE_COMPETITION.toResponseStatusException();
    }
  }

}
