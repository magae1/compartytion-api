package com.compartytion.domain.model.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Competition {

  @ToString
  @RequiredArgsConstructor
  public enum Status {
    RECRUIT("모집중"),
    READY("준비중"),
    PLAY("진행중"),
    DONE("종료");

    private final String message;
  }

  @Id
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String introduction;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn
  @ColumnDefault("1")
  @OnDelete(action = OnDeleteAction.SET_DEFAULT)
  private Account creator;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Status status = Status.RECRUIT;

  @Column(nullable = false)
  private Integer numOfParticipants;

  @Column(nullable = false, updatable = false)
  private boolean isTeamGame;

  @Column(nullable = false)
  private boolean isPublic;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;
}
