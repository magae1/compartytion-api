package com.compartytion.domain.model.entity;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.compartytion.domain.model.mixin.CreationTimeStampMixin;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends CreationTimeStampMixin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false, length = 150)
  private String username;

  @JsonIgnore // 직렬화 제외
  @Column(nullable = false)
  private String password;

  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime lastPasswordChangedAt;

  private String avatar;

  @OneToMany(mappedBy = "account")
  private List<ApplicationTemplate> applicationTemplates;


  public void changePassword(String newPassword, PasswordEncoder passwordEncoder) {
    this.password = passwordEncoder.encode(newPassword);
    this.lastPasswordChangedAt = LocalDateTime.now();
  }
}
