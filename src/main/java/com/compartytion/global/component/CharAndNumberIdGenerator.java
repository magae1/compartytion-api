package com.compartytion.global.component;


import org.springframework.stereotype.Component;

import com.compartytion.global.utils.RandomGenerator;


@Component
public class CharAndNumberIdGenerator extends RandomGenerator {


  public CharAndNumberIdGenerator() {
    super("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
  }

  @Override
  public String next(int length) {
    return super.next(length);
  }

  public String next() {
    return next(8);
  }
}
