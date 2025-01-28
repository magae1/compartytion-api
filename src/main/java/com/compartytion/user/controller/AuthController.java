package com.compartytion.user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compartytion.user.dto.EmailExistenceResponse;
import com.compartytion.user.dto.request.EmailExistenceRequest;
import com.compartytion.user.service.AuthService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/email/existence")
  public ResponseEntity<EmailExistenceResponse> checkEmailExistence(
      @RequestBody @Valid EmailExistenceRequest emailExistenceRequest) {
    String email = emailExistenceRequest.getEmail();
    EmailExistenceResponse emailExistenceResponse = authService.checkEmailExistence(email);
    return ResponseEntity.ok().body(emailExistenceResponse);
  }

}
