package com.compartytion.global.component;


import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CharAndNumberIdGeneratorTests {

  @Test
  @DisplayName("문자와 숫자 ID 생성 테스트")
  void testGeneratorCharAndNumberID() {
    CharAndNumberIdGenerator charAndNumberIdGenerator = new CharAndNumberIdGenerator();
    String id = charAndNumberIdGenerator.next();

    assertNotNull(id);
    assertTrue(Pattern.matches("[0-9A-Z]{8}", id));
  }
}
