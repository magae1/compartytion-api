package com.compartytion.global.component;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.repository.AccountRepository;


@Log4j2
@Component
@RequiredArgsConstructor
public class EmptyAccountLoader implements CommandLineRunner {

  @Value("${empty-account.email}")
  private String email;

  @Value("${empty-account.username}")
  private String username;

  @Value("${empty-account.password}")
  private String password;

  private final AccountRepository accountRepos;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    try {
      if (accountRepos.existsByEmail(email)) {
        return;
      }
      Account account = Account.builder()
          .email(email)
          .username(username)
          .password(passwordEncoder.encode(password))
          .build();
      accountRepos.save(account);
    } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
      log.error(e);
    }
  }
}
