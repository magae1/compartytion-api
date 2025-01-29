package com.compartytion.domain.user.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compartytion.domain.user.dto.EmailExistenceResponse;
import com.compartytion.domain.user.dto.EmailRequest;
import com.compartytion.domain.user.dto.SignUpRequest;
import com.compartytion.domain.user.service.AuthService;
import com.compartytion.global.dto.ActionResponse;

import static com.compartytion.domain.user.exception.AuthResponses.OTP_SENT;
import static com.compartytion.domain.user.exception.AuthResponses.SIGN_UP_COMPLETED;


@Tag(name = "Auth API", description = "인증 관련 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/email/existence")
  public ResponseEntity<EmailExistenceResponse> checkEmailExistence(
      @RequestBody @Valid EmailRequest request) {
    String email = request.email();
    EmailExistenceResponse emailExistenceResponse = authService.checkEmailExistence(email);
    return ResponseEntity.ok().body(emailExistenceResponse);
  }

  @PostMapping("/email/otp")
  public ResponseEntity<ActionResponse> sendOTP(@RequestBody @Valid EmailRequest request) {
    String email = request.email();
    authService.sendOTPByEmail(email);
    return OTP_SENT.toActionResponseEntity();
  }

  @PostMapping("/signup")
  public ResponseEntity<ActionResponse> signup(@RequestBody @Valid SignUpRequest request) {
    authService.signUp(request);
    return SIGN_UP_COMPLETED.toActionResponseEntity();
  }

}
