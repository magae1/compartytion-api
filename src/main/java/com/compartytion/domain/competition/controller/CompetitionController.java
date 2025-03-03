package com.compartytion.domain.competition.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compartytion.domain.competition.dto.CompetitionCreationDTO;
import com.compartytion.domain.competition.dto.CompetitionStatusChangeResponse;
import com.compartytion.domain.competition.dto.CompetitionTitleOnlyResponse;
import com.compartytion.domain.competition.dto.request.CompetitionCreationRequest;
import com.compartytion.domain.competition.dto.CompetitionDeletionDTO;
import com.compartytion.domain.competition.dto.CompetitionResponse;
import com.compartytion.domain.competition.dto.CompetitionModificationDTO;
import com.compartytion.domain.competition.dto.request.CompetitionModificationRequest;
import com.compartytion.domain.competition.dto.SimpleCompetitionResponse;
import com.compartytion.domain.competition.dto.CompetitionStatusChangeDTO;
import com.compartytion.domain.competition.dto.request.CompetitionStatusChangeRequest;
import com.compartytion.domain.competition.dto.SimpleCompetitionDTO;
import com.compartytion.domain.competition.service.CompetitionService;
import com.compartytion.domain.user.dto.AccountDetails;
import com.compartytion.global.dto.PageResponse;

import static com.compartytion.domain.competition.enums.CompetitionResponses.COMPETITION_CREATED;
import static com.compartytion.domain.competition.enums.CompetitionResponses.COMPETITION_DELETED;
import static com.compartytion.domain.competition.enums.CompetitionResponses.COMPETITION_MODIFIED;


@Tag(name = "Competition API", description = "대회 관련 API")
@Log4j2
@RestController
@RequestMapping("/competitions")
@RequiredArgsConstructor
public class CompetitionController {

  private static final int DEFAULT_PAGE_SIZE = 5;
  private final CompetitionService competitionService;

  @Operation(summary = "새 대회 생성 요청")
  @PostMapping
  public ResponseEntity<CompetitionResponse> createCompetition(
      @RequestBody @Valid CompetitionCreationRequest request,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    CompetitionCreationDTO creationDTO = CompetitionCreationDTO.builder()
        .title(request.title())
        .introduction(request.introduction())
        .isPublic(Boolean.FALSE)
        .isTeamGame(request.isTeamGame())
        .creatorId(accountDetails.getId())
        .build();

    Long competitionId = competitionService.createCompetition(creationDTO);
    log.info("Competition created: {}", competitionId);

    return ResponseEntity
        .status(COMPETITION_CREATED.getHttpStatus())
        .body(new CompetitionResponse(competitionId, COMPETITION_CREATED.getMessage()));
  }

  @Operation(summary = "대회 수정 요청")
  @PatchMapping("/{id}")
  public ResponseEntity<CompetitionResponse> patchCompetition(
      @RequestBody @Valid CompetitionModificationRequest request,
      @PathVariable Long id,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    CompetitionModificationDTO modificationDTO = CompetitionModificationDTO.builder()
        .title(request.title())
        .introduction(request.introduction())
        .competitionId(id)
        .accountId(accountDetails.getId())
        .build();

    Long competitionId = competitionService.modifyCompetition(modificationDTO);
    log.info("Competition modified: {}", competitionId);

    return ResponseEntity.status(COMPETITION_MODIFIED.getHttpStatus())
        .body(new CompetitionResponse(competitionId, COMPETITION_MODIFIED.getMessage()));
  }

  @Operation(summary = "대회 삭제 요청")
  @DeleteMapping("/{id}")
  public ResponseEntity<CompetitionResponse> deleteCompetition(
      @PathVariable Long id,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    CompetitionDeletionDTO deletionDTO = CompetitionDeletionDTO.builder()
        .accountId(accountDetails.getId())
        .competitionId(id)
        .build();

    Long competitionId = competitionService.deleteCompetition(deletionDTO);
    log.info("Competition deleted: {}", competitionId);

    return ResponseEntity.status(COMPETITION_DELETED.getHttpStatus())
        .body(new CompetitionResponse(competitionId, COMPETITION_DELETED.getMessage()));
  }

  @Operation(summary = "대회 상태 변경")
  @PostMapping("/{id}/change-status")
  public ResponseEntity<CompetitionStatusChangeResponse> changeCompetitionStatus(
      @PathVariable Long id,
      @RequestBody CompetitionStatusChangeRequest request,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    CompetitionStatusChangeDTO competitionStatusChangeDTO = CompetitionStatusChangeDTO.builder()
        .competitionId(id)
        .accountId(accountDetails.getId())
        .status(request.status())
        .build();

    competitionStatusChangeDTO = competitionService.changeCompetitionStatus(
        competitionStatusChangeDTO);

    return ResponseEntity.ok(
        new CompetitionStatusChangeResponse(
            competitionStatusChangeDTO.getStatus(),
            COMPETITION_MODIFIED.getMessage())
    );
  }

  @Operation(summary = "대회 간단 정보 검색")
  @GetMapping("/{id}/simple")
  public ResponseEntity<SimpleCompetitionResponse> retrieveSimpleCompetition(
      @PathVariable Long id
  ) {
    SimpleCompetitionDTO simpleCompetitionDTO = competitionService.getSimpleCompetitionDTO(id);

    return ResponseEntity.ok(new SimpleCompetitionResponse(
        simpleCompetitionDTO.getId(),
        simpleCompetitionDTO.getTitle(),
        simpleCompetitionDTO.getIntroduction(),
        simpleCompetitionDTO.getCreator(),
        simpleCompetitionDTO.getStatus().getMessage(),
        simpleCompetitionDTO.isTeamGame(),
        simpleCompetitionDTO.isPublic()));
  }

  @Operation(summary = "내 소속된 대회 목록")
  @GetMapping("/join/me")
  public ResponseEntity<PageResponse<CompetitionTitleOnlyResponse>> getJoinedCompetitionPage(
      @AuthenticationPrincipal AccountDetails accountDetails,
      @RequestParam(defaultValue = "1") int page) {
    PageResponse<CompetitionTitleOnlyResponse> pageResponse = competitionService.getJoinedCompetitionPage(
        accountDetails.getId(), PageRequest.of(Math.max(0, page - 1), DEFAULT_PAGE_SIZE));
    return ResponseEntity.ok(pageResponse);
  }
}
