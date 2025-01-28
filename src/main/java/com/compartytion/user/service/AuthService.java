package com.compartytion.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.compartytion.user.dto.EmailExistenceResponse;
import com.compartytion.user.repository.AccountRepository;


@Service
@RequiredArgsConstructor
public class AuthService {

  private final AccountRepository accountRepo;

  private boolean existsByEmail(String email) {
    return accountRepo.existsByEmail(email);
  }

  public EmailExistenceResponse checkEmailExistence(String email) {
    boolean exists = existsByEmail(email);
    return new EmailExistenceResponse(email, exists);
  }

}
