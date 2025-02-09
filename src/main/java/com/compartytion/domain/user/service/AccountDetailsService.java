package com.compartytion.domain.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.repository.AccountRepository;
import com.compartytion.domain.user.dto.AccountDetails;

import static com.compartytion.domain.user.enums.AuthExceptions.NOT_FOUND_EMAIL;


@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {

  private final AccountRepository accountRepo;

  @Override
  public AccountDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountRepo.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(NOT_FOUND_EMAIL.getMessage()));
    return new AccountDetails(account);
  }
}
