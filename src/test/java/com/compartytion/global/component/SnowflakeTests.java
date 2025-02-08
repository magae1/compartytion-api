package com.compartytion.global.component;


import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SnowflakeTests {

  @Test
  @DisplayName("snowflake ID 비트 유효성 검사")
  void givenSnowflake_whenGenerateId_thenCorrectBitsFilled() {
    // Given
    long nodeId = 784;
    Snowflake snowflake = new Snowflake(nodeId);
    long beforeTimestamp = Instant.now().toEpochMilli();

    // When
    long id = snowflake.nextId();

    // Then
    long[] attrs = snowflake.parse(id);
    assertTrue(attrs[0] >= beforeTimestamp);
    assertEquals(nodeId, attrs[1]);
    assertEquals(0, attrs[2]);
  }

  @Test
  @DisplayName("snowflake ID 유일성 검사")
  void givenSnowflake_whenGenerateId_thenBeUnique() {
    // Given
    long nodeId = 234;
    Snowflake snowflake = new Snowflake(nodeId);

    // When
    int iterations = 5000;
    long[] ids = new long[iterations];
    for (int i = 0; i < iterations; i++) {
      ids[i] = snowflake.nextId();
    }

    // Then
    for (int i = 0; i < ids.length; i++) {
      for (int j = i + 1; j < ids.length; j++) {
        assertNotEquals(ids[i], ids[j]);
      }
    }
  }

  @Test
  @DisplayName("snowflake ID 쓰레드 안전 검사")
  void givenSnowflake_whenGenerateId_thenBeUniqueIdIfCalledFromMultipleThreads()
      throws InterruptedException, ExecutionException {
    // Given
    int numOfThreads = 50;
    long nodeId = 234;
    ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
    CountDownLatch latch = new CountDownLatch(numOfThreads);
    Snowflake snowflake = new Snowflake(nodeId);

    // When
    int iterations = 10000;
    Future<Long>[] futures = new Future[iterations];
    for (int i = 0; i < iterations; i++) {
      futures[i] = executorService.submit(() -> {
        long id = snowflake.nextId();
        latch.countDown();
        return id;
      });
    }
    latch.await();
    
    // Then
    for (int i = 0; i < futures.length; i++) {
      for (int j = i + 1; j < futures.length; j++) {
        assertNotSame(futures[i].get(), futures[j].get());
      }
    }
  }
}
