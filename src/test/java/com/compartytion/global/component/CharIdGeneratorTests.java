package com.compartytion.global.component;


import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CharIdGeneratorTests {

  @Test
  @DisplayName("문자 ID 생성 테스트")
  void testGeneratorCharID() {
    CharIdGenerator charIdGenerator = new CharIdGenerator();
    String id = charIdGenerator.next();
    
    assertNotNull(id);
    assertTrue(Pattern.matches("[a-zA-Z]{8}", id));
  }
}
