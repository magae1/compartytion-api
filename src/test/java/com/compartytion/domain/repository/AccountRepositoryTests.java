package com.compartytion.domain.repository;


import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.repository.projection.SimpleAccountInfo;


@DataJpaTest
public class AccountRepositoryTests {

  @Autowired
  private AccountRepository accountRepo;


  @Test
  void testFindSimpleAccountInfoById() {
    // Given
    String username = "test-user";
    String email = "test@example.com";
    Account account = Account.builder()
        .email(email)
        .password("123456")
        .username(username)
        .build();
    Account savedAccount = accountRepo.save(account);
    Long savedId = savedAccount.getId();

    // When
    Optional<SimpleAccountInfo> result = accountRepo.findSimpleAccountInfoById(savedId);

    // Then
    assertFalse(result.isEmpty());
    assertEquals(username, result.get().username());
    assertEquals(email, result.get().email());
  }
}
