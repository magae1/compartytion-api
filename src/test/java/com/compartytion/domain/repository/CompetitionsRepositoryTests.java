package com.compartytion.domain.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.model.entity.Competition;
import com.compartytion.global.component.Snowflake;


@RunWith(SpringRunner.class)
@Import(Snowflake.class)
@DataJpaTest
public class CompetitionsRepositoryTests {

  @Autowired
  private CompetitionRepository competitionRepo;

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private Snowflake snowflake;

  @Test
  @DisplayName("Snowflake를 이용한 대회 엔티티 삽입")
  void givenSnowflakeId_whenInsertCompetition_thenEntitySaved() {
    // Given
    Long id = snowflake.nextId();
    Account account = Account.builder()
        .email("test@example.com")
        .username("test-user")
        .password("123456")
        .build();
    accountRepo.save(account);
    Competition competition = Competition.builder()
        .id(id)
        .creator(account)
        .title("test competition")
        .introduction("Hi! It's test competition.")
        .isPublic(false)
        .isTeamGame(false)
        .build();

    // When
    Competition savedCompetition = competitionRepo.save(competition);

    // Then
    assertEquals(id, savedCompetition.getId());
  }

}
