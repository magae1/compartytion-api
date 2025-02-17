package com.compartytion.global.component;


import org.springframework.stereotype.Component;

import com.compartytion.global.utils.RandomGenerator;


@Component
public class CharIdGenerator extends RandomGenerator {


  public CharIdGenerator() {
    super("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
  }

  @Override
  public String next(int length) {
    return super.next(length);
  }

  public String next() {
    return next(8);
  }
}
