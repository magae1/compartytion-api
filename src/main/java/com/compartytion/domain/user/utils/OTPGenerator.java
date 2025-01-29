package com.compartytion.domain.user.utils;


import java.util.concurrent.ThreadLocalRandom;


public class OTPGenerator {

  public static final String SEED = "0123456789";

  public static String generateOTP(int length) {
    StringBuffer otpBuffer = new StringBuffer(length);
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int i = 0; i < length; i++) {
      otpBuffer.append(SEED.charAt(random.nextInt(SEED.length())));
    }
    return otpBuffer.toString();
  }

  public static String generateOTP() {
    return generateOTP(6);
  }

}
