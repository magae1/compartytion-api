package com.compartytion.domain.application.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.compartytion.domain.application.dto.ApplicationCreationDTO;
import com.compartytion.domain.application.dto.request.ApplicationCreationRequest;
import com.compartytion.domain.application.dto.response.ApplicationCreationResponse;
import com.compartytion.domain.application.service.ApplicationService;
import com.compartytion.domain.user.dto.AccountDetails;

import static com.compartytion.domain.application.enums.ApplicationResponses.APPLICATION_CREATED;


@Tag(name = "Application API", description = "지원서 관련 API")
@Log4j2
@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

  private final ApplicationService applicationService;

  @Operation(summary = "지원서 작성 요청")
  @PostMapping
  public ResponseEntity<?> postApplication(
      @RequestBody @Valid ApplicationCreationRequest request,
      @AuthenticationPrincipal AccountDetails accountDetails
  ) throws MethodArgumentNotValidException, ResponseStatusException {
    ApplicationCreationDTO.ApplicationCreationDTOBuilder builder = ApplicationCreationDTO.builder();
    builder.competitionId(request.competitionId());
    builder.email(request.email());
    builder.password(request.password());
    builder.confirmedPassword(request.confirmedPassword());
    builder.displayedName(request.displayedName());
    builder.hiddenName(request.hiddenName());
    builder.shortIntroduction(request.shortIntroduction());
    if (accountDetails != null) {
      builder.accountId(accountDetails.getId());
      builder.isAuthenticated(true);
    } else {
      builder.isAuthenticated(false);
    }
    String identifier = applicationService.addApplication(builder.build());

    if (accountDetails != null) {
      return APPLICATION_CREATED.toActionResponseEntity();
    } else {
      return new ResponseEntity<>(
          new ApplicationCreationResponse(identifier, APPLICATION_CREATED.getMessage()),
          HttpStatus.CREATED);
    }
  }

}
