package com.compartytion.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


@Builder
@Getter
@AllArgsConstructor
@RedisHash("unauthenticated-email")
public class UnauthenticatedEmail {

  @Id
  private String email;

  private String otp;

  @TimeToLive
  private Long expiration;

  @Builder.Default
  private boolean isVerified = false;

}
