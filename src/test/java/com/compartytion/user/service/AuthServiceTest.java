package com.compartytion.user.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.compartytion.user.dto.EmailExistenceResponse;
import com.compartytion.user.repository.AccountRepository;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

  @Mock
  private AccountRepository accountRepo;

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
    assertTrue(res.isExists());
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
    assertFalse(res.isExists());
  }

}
