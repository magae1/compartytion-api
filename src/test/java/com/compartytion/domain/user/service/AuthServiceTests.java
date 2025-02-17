package com.compartytion.domain.user.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.compartytion.domain.user.dto.EmailExistenceResponse;
import com.compartytion.domain.repository.AccountRepository;
import com.compartytion.domain.repository.UnauthenticatedEmailRepository;
import com.compartytion.global.component.OTPGenerator;
import com.compartytion.global.component.EmailSender;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTests {

  @Mock
  private AccountRepository accountRepo;

  @Mock
  private UnauthenticatedEmailRepository unauthenticatedEmailRepo;

  @Mock
  private EmailSender emailSender;

  @Mock
  private OTPGenerator otpGenerator;

  @InjectMocks
  private AuthService authService;


  @Test
  @DisplayName("특정 이메일을 가진 유저 정보가 있으면, 이메일 존재 여부 확인 시 true를 반환")
  void givenAccount_whenCheckEmailExistence_thenReturnsTrue() throws Exception {
    // Given
    String email = "test@example.com";
    when(accountRepo.existsByEmail(email)).thenReturn(true);

    // When
    EmailExistenceResponse res = authService.checkEmailExistence(email);

    // Then
    assertTrue(res.exists());
  }

  @Test
  @DisplayName("특정 이메일을 가진 유저 정보가 없으면, 이메일 존재 여부 확인 시 false를 반환")
  void notGivenAccount_whenCheckEmailExistence_thenReturnsFalse() throws Exception {
    // Given
    String email = "test@example.com";
    when(accountRepo.existsByEmail(email)).thenReturn(false);

    // When
    EmailExistenceResponse res = authService.checkEmailExistence(email);

    // Then
    assertFalse(res.exists());
  }

  @Test
  @DisplayName("특정 이메일을 가진 유저 정보가 있으면, 예외를 반환")
  void givenAccount_whenSendOTPForSignup_thenThrowsResponseStatusException() throws Exception {
    // Given
    String email = "test@example.com";
    when(accountRepo.existsByEmail(email)).thenReturn(true);

    // Then
    assertThrows(ResponseStatusException.class, () -> {
      // When
      authService.sendOTPForSignup(email);
    });
  }

  @Test
  @DisplayName("특정 이메일을 가진 유저 정보가 없으면, 예외를 반환하지 않음")
  void givenAccount_whenSendOTPForSignup_thenNotThrowException() throws Exception {
    // Given
    String email = "test@example.com";
    when(otpGenerator.next()).thenReturn("123456");
    when(accountRepo.existsByEmail(email)).thenReturn(false);

    // Then
    assertDoesNotThrow(() -> {
      // When
      authService.sendOTPForSignup(email);
    });
  }

}
