package com.compartytion.domain.application.mapper;


import org.springframework.security.crypto.password.PasswordEncoder;

import com.compartytion.domain.application.dto.ApplicationCreationDTO;
import com.compartytion.domain.application.dto.UnAuthApplicationCreationDTO;
import com.compartytion.domain.application.dto.request.ApplicationCreationRequest;
import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.model.entity.Application;
import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.user.dto.AccountDetails;

public class ApplicationMapper {

  public static ApplicationCreationDTO toApplicationCreationDTO(ApplicationCreationRequest request,
      AccountDetails accountDetails) {
    return ApplicationCreationDTO.builder()
        .competitionId(request.competitionId())
        .accountId(accountDetails.getId())
        .email(request.email())
        .name(request.name())
        .shortIntroduction(request.shortIntroduction())
        .build();
  }

  public static UnAuthApplicationCreationDTO toUnAuthApplicationCreationDTO(
      ApplicationCreationRequest request) {
    return UnAuthApplicationCreationDTO.builder()
        .competitionId(request.competitionId())
        .email(request.email())
        .password(request.password())
        .confirmedPassword(request.confirmedPassword())
        .name(request.name())
        .shortIntroduction(request.shortIntroduction())
        .build();
  }

  public static Application toEntity(ApplicationCreationDTO creationDTO, String identifier) {
    return Application.builder()
        .competition(Competition.builder().id(creationDTO.getCompetitionId()).build())
        .account(Account.builder().id(creationDTO.getAccountId()).build())
        .identifier(identifier)
        .email(creationDTO.getEmail())
        .name(creationDTO.getName())
        .shortIntroduction(creationDTO.getShortIntroduction())
        .build();
  }

  public static Application toEntity(UnAuthApplicationCreationDTO creationDTO, String identifier,
      PasswordEncoder encoder) {
    return Application.builder()
        .competition(Competition.builder().id(creationDTO.getCompetitionId()).build())
        .identifier(identifier)
        .email(creationDTO.getEmail())
        .password(encoder.encode(creationDTO.getPassword()))
        .name(creationDTO.getName())
        .shortIntroduction(creationDTO.getShortIntroduction())
        .build();
  }

}
