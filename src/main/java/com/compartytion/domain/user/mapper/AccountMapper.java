package com.compartytion.domain.user.mapper;


import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.user.dto.SignUpRequest;


public class AccountMapper {

  public static Account toEntity(SignUpRequest signUpRequest, String encryptedPassword) {
    return Account.builder()
        .email(signUpRequest.email())
        .username(signUpRequest.username())
        .password(encryptedPassword)
        .build();
  }

}
