package com.compartytion.domain.repository.projection;


import java.time.LocalDateTime;


public record DetailAccountInfo(
    String email,
    String username,
    LocalDateTime createdAt,
    LocalDateTime lastPasswordChangedAt,
    String avatar
) {

}
