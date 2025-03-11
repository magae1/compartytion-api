package com.compartytion.domain.application.mapper;


import com.compartytion.domain.application.dto.ApplicationCreationDTO;
import com.compartytion.domain.application.dto.ApplicationPasswordDTO;

public class ApplicationMapper {

  public static ApplicationPasswordDTO toPasswordDTO(ApplicationCreationDTO creationDTO) {
    return ApplicationPasswordDTO.builder()
        .password(creationDTO.getPassword())
        .confirmedPassword(creationDTO.getConfirmedPassword())
        .build();
  }

}
