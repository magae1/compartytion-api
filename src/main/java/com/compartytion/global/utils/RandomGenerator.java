package com.compartytion.global.utils;


import java.util.concurrent.ThreadLocalRandom;


public class RandomGenerator {

  public final String SEED;

  public RandomGenerator(String seed) {
    SEED = seed;
  }

  public String next(int length) {
    StringBuilder stringBuilder = new StringBuilder(length);
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int i = 0; i < length; i++) {
      stringBuilder.append(SEED.charAt(random.nextInt(SEED.length())));
    }
    return stringBuilder.toString();
  }

}
