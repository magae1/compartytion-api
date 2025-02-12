package com.compartytion.domain.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compartytion.domain.repository.projection.DetailAccountInfo;
import com.compartytion.domain.repository.projection.SimpleAccountInfo;
import com.compartytion.domain.user.dto.AccountDetails;
import com.compartytion.domain.user.dto.DetailAccountResponse;
import com.compartytion.domain.user.dto.SimpleAccountResponse;
import com.compartytion.domain.user.mapper.AccountMapper;
import com.compartytion.domain.user.service.AccountService;


@Tag(name = "Account API", description = "계정 관련 API")
@Log4j2
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  public ResponseEntity<SimpleAccountResponse> getMySimpleAccount(
      @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    SimpleAccountInfo simpleAccountInfo = accountService.getSimpleAccountInfo(
        accountDetails.getId());
    return ResponseEntity.ok(AccountMapper.toSimpleAccountResponse(simpleAccountInfo));
  }

  @Operation(summary = "계정 간단 정보 조회")
  @GetMapping("/simple")
  public ResponseEntity<SimpleAccountResponse> getSimpleAccount(
      @RequestParam("email") String email
  ) {
    SimpleAccountInfo simpleAccountInfo = accountService.getSimpleAccountInfo(email);
    if (simpleAccountInfo == null) {
      return ResponseEntity.ok(null);
    }
    return ResponseEntity.ok(AccountMapper.toSimpleAccountResponse(simpleAccountInfo));
  }

  @Operation(summary = "본인 계정 상세 정보 조회")
  @GetMapping("/detail/me")
  public ResponseEntity<DetailAccountResponse> getDetailAccount(
      @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    DetailAccountInfo detailAccountInfo = accountService.getDetailAccountInfo(
        accountDetails.getId());
    return ResponseEntity.ok(AccountMapper.toDetailAccountResponse(detailAccountInfo));
  }

}
