package com.compartytion.domain.repository;


import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.compartytion.domain.competition.dto.CompetitionCreationDTO;
import com.compartytion.domain.competition.mapper.CompetitionMapper;
import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.model.entity.Competition.Status;
import com.compartytion.domain.model.entity.Participant;
import com.compartytion.domain.repository.projection.IdAndTitleOnly;


@DataJpaTest
public class CompetitionsRepositoryTests {

  @Autowired
  private CompetitionRepository competitionRepo;

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private ParticipantRepository participantRepo;


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

  @Test
  @DisplayName("참여 중인 대회 조회 테스트")
  void testFindJoinedCompetitionsByAccountId() {
    // Given
    Account account1 = Account.builder()
        .email("test1@example.com")
        .username("test-user1")
        .password("123456")
        .build();
    Account account2 = Account.builder()
        .email("test2@example.com")
        .username("test-user2")
        .password("123456")
        .build();
    accountRepo.saveAll(List.of(account1, account2));

    Competition competition1 = Competition.builder()
        .creator(account1)
        .title("test competition1")
        .introduction("Hi! It's test competition.")
        .isPublic(false)
        .isTeamGame(false)
        .build();
    Competition competition2 = Competition.builder()
        .creator(account2)
        .title("test competition2")
        .introduction("Hi! It's test competition.")
        .isPublic(false)
        .isTeamGame(false)
        .participants(List.of(Participant.builder()
            .account(account1)
            .displayedName("participant1")
            .hiddenName("part-test-user1")
            .identifier("123456")
            .index(0)
            .build()))
        .build();
    competitionRepo.saveAll(List.of(competition1, competition2));
    Participant participant = Participant.builder()
        .account(account1)
        .competition(competition2)
        .displayedName("test-displayed-name")
        .hiddenName("test-hidden-name")
        .identifier("asdfgh")
        .index(0)
        .build();
    participantRepo.save(participant);

    //Then
    assertDoesNotThrow(() -> {
      // When
      Page<IdAndTitleOnly> page = competitionRepo.findJoinedCompetitionsByAccountId(
          account1.getId(), PageRequest.of(0, 5));
      System.out.println(page);
      assertEquals(2, page.getTotalElements());
      assertEquals(1, page.getTotalPages());
    });
  }

}
