package com.compartytion.domain.user.mapper;


import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.repository.projection.DetailAccountInfo;
import com.compartytion.domain.repository.projection.SimpleAccountInfo;
import com.compartytion.domain.user.dto.response.DetailAccountResponse;
import com.compartytion.domain.user.dto.request.SignUpRequest;
import com.compartytion.domain.user.dto.SimpleAccountDTO;
import com.compartytion.domain.user.dto.response.SimpleAccountResponse;


public class AccountMapper {

  public static Account toEntity(SignUpRequest signUpRequest, String encryptedPassword) {
    return Account.builder()
        .email(signUpRequest.email())
        .username(signUpRequest.username())
        .password(encryptedPassword)
        .build();
  }

  public static SimpleAccountResponse toSimpleAccountResponse(SimpleAccountInfo simpleAccountInfo) {
    return new SimpleAccountResponse(
        simpleAccountInfo.username(),
        simpleAccountInfo.email(),
        simpleAccountInfo.avatar()
    );
  }

  public static DetailAccountResponse toDetailAccountResponse(DetailAccountInfo detailAccountInfo) {
    return new DetailAccountResponse(
        detailAccountInfo.username(),
        detailAccountInfo.email(),
        detailAccountInfo.avatar(),
        detailAccountInfo.createdAt(),
        detailAccountInfo.lastPasswordChangedAt()
    );
  }

  public static SimpleAccountDTO toSimpleAccountDTO(Account account) {
    return SimpleAccountDTO.builder()
        .username(account.getUsername())
        .avatar(account.getAvatar())
        .build();
  }
}
