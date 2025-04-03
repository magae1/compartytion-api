package com.compartytion.domain.repository;


import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

  private Account creatorAccount;
  private Competition competition;

  @BeforeEach
  void setUp() {
    creatorAccount = accountRepo.save(Account.builder()
        .email("test@example.com")
        .username("test-user")
        .password("123456")
        .build());
    competition = competitionRepo.save(Competition.builder()
        .creator(creatorAccount)
        .title("test competition")
        .introduction("Hi! It's test competition.")
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
    boolean exists = participantRepo.existsByCompetitionIdAndAccountId(competition.getId(),
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
        .competition(competition)
        .email(participantAccount.getEmail())
        .name("participant")
        .identifier("123456")
        .index(0)
        .account(participantAccount)
        .build());

    // When
    boolean exists = participantRepo.existsByCompetitionIdAndAccountId(competition.getId(),
        participantAccount.getId());

    // Then
    assertTrue(exists);
  }

  @Test
  void testFindAllIdentifier() {
    // Given
    Account participantAccount = Account.builder()
        .email("test2@example.com")
        .username("test-participant")
        .password("123456")
        .build();
    participantAccount = accountRepo.save(participantAccount);

    Participant participant = Participant.builder()
        .account(participantAccount)
        .competition(competition)
        .index(0)
        .email("test@example.com")
        .name("participant")
        .identifier("basedf")
        .build();
    participantRepo.save(participant);
    Long competitionId = competition.getId();

    // When
    Set<?> identifierSet = participantRepo.findAllIdentifier(competitionId);

    // Then
    assertEquals(Set.of("basedf"), identifierSet);
  }

}
