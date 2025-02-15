package com.compartytion.domain.competition.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.compartytion.domain.competition.dto.CompetitionCreationDTO;
import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.repository.CompetitionRepository;
import com.compartytion.global.component.Snowflake;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class CompetitionServiceTests {

  @Mock
  private CompetitionRepository competitionRepo;

  @Mock
  private Snowflake snowflake;

  @InjectMocks
  private CompetitionService competitionService;


  @Test
  @DisplayName("대회 생성 테스트")
  void givenCreationDTO_whenCreateCompetition_thenCompetitionSaved() {
    // Given
    CompetitionCreationDTO creationDTO = CompetitionCreationDTO.builder()
        .title("test competition")
        .introduction("Hi! It's test competition")
        .numOfParticipants(16)
        .isTeamGame(false)
        .isPublic(false)
        .creatorId(1L)
        .build();
    Competition competition = Competition.builder()
        .id(1L)
        .title("test competition")
        .introduction("Hi! It's test competition")
        .numOfParticipants(16)
        .isTeamGame(false)
        .isPublic(false)
        .creator(Account.builder().id(1L).build())
        .build();
    when(snowflake.nextId()).thenReturn(1L);
    when(competitionRepo.save(any()))
        .thenReturn(competition);

    // When
    Long id = competitionService.createCompetition(creationDTO);

    // Then
    assertEquals(1L, id);
  }

}
