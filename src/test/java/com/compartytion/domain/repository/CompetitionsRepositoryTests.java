package com.compartytion.domain.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.compartytion.domain.competition.dto.CompetitionCreationDTO;
import com.compartytion.domain.competition.mapper.CompetitionMapper;
import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.model.entity.Competition.Status;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CompetitionsRepositoryTests {

  @Autowired
  private CompetitionRepository competitionRepo;

  @Autowired
  private AccountRepository accountRepo;

  @Test
  @DisplayName("대회 생성 테스트")
  void givenCreationDTO_whenInsertCompetition_thenEntitySaved() {
    // Given
    Account account = Account.builder()
        .email("test@example.com")
        .username("test-user")
        .password("123456")
        .build();
    accountRepo.save(account);

    CompetitionCreationDTO creationDTO = CompetitionCreationDTO.builder()
        .creatorId(account.getId())
        .title("test competition")
        .introduction("Hi! It's test competition.")
        .isPublic(false)
        .isTeamGame(false)
        .build();

    // Then
    assertDoesNotThrow(() -> {
      // When
      competitionRepo.save(CompetitionMapper.toEntity(creationDTO));
    });
  }

  @Test
  @DisplayName("잘못된 account_id를 이용한 대회 생성 테스트")
  void givenWrongAccountIdCreationDTO_whenInsertCompetition_thenThrowException() {
    // Given
    CompetitionCreationDTO creationDTO = CompetitionCreationDTO.builder()
        .creatorId(100L)
        .title("test competition")
        .introduction("Hi! It's test competition.")
        .isPublic(false)
        .isTeamGame(false)
        .build();

    // Then
    assertThrows(RuntimeException.class, () -> {
      // When
      competitionRepo.save(CompetitionMapper.toEntity(creationDTO));
    });
  }

  @Test
  @DisplayName("대회 삭제 테스트")
  void givenCompetitionId_whenDeleteByCompetitionId_thenEntityDeleted() {
    // Given
    Account account = Account.builder()
        .email("test@example.com")
        .username("test-user")
        .password("123456")
        .build();
    accountRepo.save(account);
    Competition competition = Competition.builder()
        .creator(account)
        .title("test competition")
        .introduction("Hi! It's test competition.")
        .isPublic(false)
        .isTeamGame(false)
        .build();
    competition = competitionRepo.save(competition);
    Long competitionId = competition.getId();

    // Then
    assertDoesNotThrow(() -> {
      // When
      competitionRepo.deleteByCompetitionId(competitionId);
    });
  }

  @Test
  @DisplayName("대회 상태 변경 테스트")
  void givenNewStatus_whenUpdateStatusById_thenCompetitionStatusChanged() {
    // Given
    Account account = Account.builder()
        .email("test@example.com")
        .username("test-user")
        .password("123456")
        .build();
    accountRepo.save(account);
    Competition competition = Competition.builder()
        .creator(account)
        .title("test competition")
        .introduction("Hi! It's test competition.")
        .isPublic(false)
        .isTeamGame(false)
        .build();
    competitionRepo.save(competition);
    Status status = Status.READY;

    // Then
    assertDoesNotThrow(() -> {
      // When
      competitionRepo.updateStatusById(competition.getId(), status);
    });
  }
}
