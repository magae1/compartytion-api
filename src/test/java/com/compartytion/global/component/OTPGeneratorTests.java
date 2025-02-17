package com.compartytion.global.component;


import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class OTPGeneratorTests {

  @Test
  @DisplayName("OTP 생성 테스트")
  void testGenerateOTP() {
    OTPGenerator otpGenerator = new OTPGenerator();
    String otp = otpGenerator.next();
    assertNotNull(otp);
    assertTrue(Pattern.matches("[0-9]{6}", otp));
  }

}
