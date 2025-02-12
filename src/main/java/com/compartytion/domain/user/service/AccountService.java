package com.compartytion.domain.user.service;


import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.compartytion.domain.repository.AccountRepository;
import com.compartytion.domain.repository.projection.DetailAccountInfo;
import com.compartytion.domain.repository.projection.SimpleAccountInfo;


@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepo;

  public SimpleAccountInfo getSimpleAccountInfo(Long accountId) {
    Optional<SimpleAccountInfo> result = accountRepo.findSimpleAccountInfoById(accountId);
    return result.orElse(null);
  }

  public SimpleAccountInfo getSimpleAccountInfo(String email) {
    Optional<SimpleAccountInfo> result = accountRepo.findSimpleAccountInfoByEmail(email);
    return result.orElse(null);
  }

  public DetailAccountInfo getDetailAccountInfo(Long accountId) {
    Optional<DetailAccountInfo> result = accountRepo.findDetailAccountInfoById(accountId);
    return result.orElse(null);
  }
}
