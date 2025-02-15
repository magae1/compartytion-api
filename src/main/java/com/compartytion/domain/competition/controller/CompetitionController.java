package com.compartytion.domain.competition.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compartytion.domain.competition.dto.CompetitionCreationDTO;
import com.compartytion.domain.competition.dto.CompetitionCreationRequest;
import com.compartytion.domain.competition.dto.CompetitionCreationResponse;
import com.compartytion.domain.competition.service.CompetitionService;
import com.compartytion.domain.user.dto.AccountDetails;


@Log4j2
@RestController
@RequestMapping("/competitions")
@RequiredArgsConstructor
public class CompetitionController {

  private final CompetitionService competitionService;

  @Operation(summary = "새 대회 생성 요청")
  @PostMapping
  public ResponseEntity<CompetitionCreationResponse> createCompetition(
      @RequestBody @Valid CompetitionCreationRequest request,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    log.debug("Request Body: {}, Account ID: {}", request, accountDetails.getId());
    CompetitionCreationDTO creationDTO = CompetitionCreationDTO.builder()
        .title(request.title())
        .introduction(request.introduction())
        .isPublic(request.isPublic())
        .isTeamGame(request.isTeamGame())
        .creatorId(accountDetails.getId())
        .build();
    Long competitionId = competitionService.createCompetition(creationDTO);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new CompetitionCreationResponse(competitionId, "대회가 생성됐습니다."));
  }

}
