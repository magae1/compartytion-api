package com.compartytion.global.component;


import org.springframework.stereotype.Component;

import com.compartytion.global.utils.RandomGenerator;


@Component
public class OTPGenerator extends RandomGenerator {

  public OTPGenerator() {
    super("0123456789");
  }

  @Override
  public String next(int length) {
    return super.next(length);
  }

  public String next() {
    return next(6);
  }

}
