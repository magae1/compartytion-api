package com.compartytion.domain.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compartytion.domain.user.dto.EmailExistenceResponse;
import com.compartytion.domain.user.dto.EmailOTPRequest;
import com.compartytion.domain.user.dto.EmailOTPResponse;
import com.compartytion.domain.user.dto.EmailRequest;
import com.compartytion.domain.user.dto.PasswordChangeRequest;
import com.compartytion.domain.user.dto.SignUpRequest;
import com.compartytion.domain.user.service.AuthService;
import com.compartytion.global.dto.ActionResponse;

import static com.compartytion.domain.user.enums.AuthResponses.EMAIL_VERIFIED;
import static com.compartytion.domain.user.enums.AuthResponses.PASSWORD_CHANGED;
import static com.compartytion.domain.user.enums.AuthResponses.SIGN_UP_COMPLETED;


@Tag(name = "Auth API", description = "인증 관련 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @Operation(summary = "이메일 존재 여부 요청")
  @PostMapping("/email/existence")
  public ResponseEntity<EmailExistenceResponse> checkEmailExistence(
      @RequestBody @Valid EmailRequest request) {
    EmailExistenceResponse emailExistenceResponse = authService.checkEmailExistence(
        request.email());
    return ResponseEntity.ok().body(emailExistenceResponse);
  }

  @Operation(summary = "회원 가입을 위한 OTP 전송 요청")
  @PostMapping("/email/send-otp")
  public ResponseEntity<EmailOTPResponse> sendEmailOTP(
      @RequestBody @Valid EmailRequest request) {
    EmailOTPResponse res = authService.sendOTPForSignup(request.email());
    return ResponseEntity.ok().body(res);
  }

  @Operation(summary = "회원 가입을 위한 OTP 확인 요청")
  @PostMapping("/email/verify-otp")
  public ResponseEntity<ActionResponse> verifyEmailOTP(
      @RequestBody @Valid EmailOTPRequest request) {
    authService.verifyOTPForSignup(request);
    return EMAIL_VERIFIED.toActionResponseEntity();
  }

  @Operation(summary = "비밀번호 변경을 위한 OTP 전송 요청")
  @PostMapping("/password/send-otp")
  public ResponseEntity<EmailOTPResponse> sendPasswordOTP(
      @RequestBody @Valid EmailRequest request) {
    EmailOTPResponse res = authService.sendOTPForChangePassword(request.email());
    return ResponseEntity.ok().body(res);
  }

  @Operation(summary = "비밀번호 변경을 위한 OTP 확인 요청")
  @PostMapping("/password/verify-otp")
  public ResponseEntity<ActionResponse> verifyPasswordOTP(
      @RequestBody @Valid EmailOTPRequest request) {
    authService.verifyOTPForChangePassword(request);
    return EMAIL_VERIFIED.toActionResponseEntity();
  }

  @Operation(summary = "비밀번호 변경 요청")
  @PostMapping("/password/change")
  public ResponseEntity<ActionResponse> changePassword(
      @RequestBody @Valid PasswordChangeRequest request,
      @RequestParam String email
  ) {
    authService.changePassword(email, request);
    return PASSWORD_CHANGED.toActionResponseEntity();
  }

  @Operation(summary = "회원가입 요청")
  @PostMapping("/signup")
  public ResponseEntity<ActionResponse> signup(
      @RequestBody @Valid SignUpRequest request) {
    authService.signUp(request);
    return SIGN_UP_COMPLETED.toActionResponseEntity();
  }

}
