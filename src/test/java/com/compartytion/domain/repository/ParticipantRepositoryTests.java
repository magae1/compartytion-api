package com.compartytion.domain.repository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.model.entity.Participant;


@DataJpaTest
public class ParticipantRepositoryTests {

  @Autowired
  private ParticipantRepository participantRepo;

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private CompetitionRepository competitionRepo;

  private Competition testCompetition;

  @BeforeEach
  void init() {
    Account testAccount = accountRepo.save(Account.builder()
        .email("test@example.com")
        .password("123456")
        .username("test-user")
        .build());
    testCompetition = competitionRepo.save(Competition.builder()
        .creator(testAccount)
        .title("test competition")
        .introduction("Hi! It's a test competition.")
        .isPublic(false)
        .isTeamGame(false)
        .build());
  }

  @Test
  void givenNoParticipantAccount_whenExistsByCompetitionIdAndAccountId_thenReturnFalse() {
    // Given
    Account participantAccount = Account.builder()
        .email("test1@example.com")
        .password("123456")
        .username("test-participant")
        .build();
    accountRepo.save(participantAccount);

    // When
    boolean exists = participantRepo.existsByCompetitionIdAndAccountId(testCompetition.getId(),
        participantAccount.getId());

    // Then
    assertFalse(exists);
  }

  @Test
  void givenParticipantAccount_whenExistsByCompetitionIdAndAccountId_thenReturnTrue() {
    // Given
    Account participantAccount = Account.builder()
        .email("test1@example.com")
        .password("123456")
        .username("test-participant")
        .build();
    accountRepo.save(participantAccount);
    participantRepo.save(Participant.builder()
        .competition(testCompetition)
        .displayedName("displayed participant")
        .hiddenName("hidden participant")
        .identifier("123456")
        .index(0)
        .account(participantAccount)
        .build());

    // When
    boolean exists = participantRepo.existsByCompetitionIdAndAccountId(testCompetition.getId(),
        participantAccount.getId());

    // Then
    assertTrue(exists);
  }
}
