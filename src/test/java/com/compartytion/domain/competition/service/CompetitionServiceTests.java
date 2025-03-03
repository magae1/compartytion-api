package com.compartytion.domain.competition.service;


import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.compartytion.domain.competition.dto.CompetitionCreationDTO;
import com.compartytion.domain.competition.dto.CompetitionTitleOnlyResponse;
import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.repository.CompetitionRepository;
import com.compartytion.domain.repository.projection.IdAndTitleOnly;
import com.compartytion.global.dto.PageResponse;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class CompetitionServiceTests {

  @Mock
  private CompetitionRepository competitionRepo;

  @InjectMocks
  private CompetitionService competitionService;


  @Test
  @DisplayName("대회 생성 테스트")
  void givenCreationDTO_whenCreateCompetition_thenCompetitionSaved() {
    // Given
    CompetitionCreationDTO creationDTO = CompetitionCreationDTO.builder()
        .title("test competition")
        .introduction("Hi! It's test competition")
        .isTeamGame(false)
        .isPublic(false)
        .creatorId(1L)
        .build();
    Competition competition = Competition.builder()
        .id(1L)
        .title("test competition")
        .introduction("Hi! It's test competition")
        .isTeamGame(false)
        .isPublic(false)
        .creator(Account.builder().id(1L).build())
        .build();
    when(competitionRepo.save(any()))
        .thenReturn(competition);

    // When
    Long id = competitionService.createCompetition(creationDTO);

    // Then
    assertEquals(1L, id);
  }

  @Test
  @DisplayName("참여 중인 대회 조회 테스트")
  void testGetJoinedCompetitionPage() {
    // Given
    Long accountId = 1L;
    Pageable pageable = PageRequest.of(0, 5);
    List<IdAndTitleOnly> contents = List.of(
        new IdAndTitleOnly() {
          @Override
          public Long getId() {
            return 1L;
          }

          @Override
          public String getTitle() {
            return "test1";
          }
        },
        new IdAndTitleOnly() {
          @Override
          public Long getId() {
            return 2L;
          }

          @Override
          public String getTitle() {
            return "test2";
          }
        });
    when(competitionRepo.findJoinedCompetitionsByAccountId(any(), any())).thenReturn(
        new PageImpl<>(contents, pageable, contents.size())
    );

    // When
    PageResponse<CompetitionTitleOnlyResponse> response = competitionService.getJoinedCompetitionPage(
        accountId, pageable);

    // Then
    assertEquals(2, response.getTotalCount());
    assertEquals(1, response.getPage());
    assertEquals(5, response.getSize());
    assertEquals(List.of(
            new CompetitionTitleOnlyResponse(1L, "test1"),
            new CompetitionTitleOnlyResponse(2L, "test2")),
        response.getResults());
  }
}
