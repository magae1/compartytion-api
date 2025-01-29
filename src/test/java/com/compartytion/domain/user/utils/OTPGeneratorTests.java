package com.compartytion.domain.user.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class OTPGeneratorTests {

  @Test
  @DisplayName("OTP 생성 테스트")
  void testGenerateOTP() {
    String otp = OTPGenerator.generateOTP();
    assertNotNull(otp);
    assertEquals(6, otp.length());
  }

}
