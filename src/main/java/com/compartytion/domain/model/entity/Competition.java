package com.compartytion.domain.model.entity;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.compartytion.domain.model.mixin.CreationTimeStampMixin;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Competition extends CreationTimeStampMixin {

  @ToString
  @Getter
  @RequiredArgsConstructor
  public enum Status {
    RECRUIT("모집중"),
    READY("준비중"),
    PLAY("진행중"),
    DONE("종료");

    private static final Map<Status, Status> transitionMap = Map.of(
        RECRUIT, READY,
        READY, PLAY,
        PLAY, DONE
    );

    private final String message;

    public static Optional<Status> getNextStatus(Status status) {
      Status result = transitionMap.get(status);

      if (result == null) {
        return Optional.empty();
      }
      return Optional.of(result);
    }
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String introduction;

  @ManyToOne(optional = false)
  @JoinColumn
  @ColumnDefault("1")
  @OnDelete(action = OnDeleteAction.SET_DEFAULT)
  private Account creator;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Status status = Status.RECRUIT;

  @Column(nullable = false, updatable = false)
  private boolean isTeamGame;

  @Column(nullable = false)
  private boolean isPublic;

  @OneToMany(mappedBy = "competition")
  List<Team> teams;

  @OneToMany(mappedBy = "competition")
  List<Rule> rules;

  @OneToMany(mappedBy = "competition")
  List<Participant> participants;

}
