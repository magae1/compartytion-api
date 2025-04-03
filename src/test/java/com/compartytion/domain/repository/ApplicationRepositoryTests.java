package com.compartytion.domain.repository;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.model.entity.Application;
import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.model.entity.Participant;


@DataJpaTest
public class ApplicationRepositoryTests {

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private CompetitionRepository competitionRepo;

  @Autowired
  private ApplicationRepository applicationRepo;

  @Autowired
  private ParticipantRepository participantRepo;

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
  void testFindAllIdentifierInParticipantsAndApplicants() {
    // Given
    Account applicantAccount = Account.builder()
        .email("test1@example.com")
        .username("test-applicant")
        .password("123456")
        .build();
    applicantAccount = accountRepo.save(applicantAccount);
    Account participantAccount = Account.builder()
        .email("test2@example.com")
        .username("test-participant")
        .password("123456")
        .build();
    participantAccount = accountRepo.save(participantAccount);

    Application application = Application.builder()
        .account(applicantAccount)
        .competition(competition)
        .name("applicant")
        .identifier("abcdef")
        .build();
    applicationRepo.save(application);
    Participant participant = Participant.builder()
        .account(participantAccount)
        .competition(competition)
        .index(0)
        .name("participant")
        .identifier("basedf")
        .build();
    participantRepo.save(participant);
    Long competitionId = competition.getId();

    // When
    Set<?> identifierSet = applicationRepo.findAllIdentifierInParticipantsAndApplicants(
        competitionId);

    // Then
    assertEquals(Set.of("abcdef", "basedf"), identifierSet);
  }
}
