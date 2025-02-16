package com.compartytion.domain.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.compartytion.domain.model.entity.key.CompetitionSubId;
import com.compartytion.domain.model.mixin.CreationTimeStampMixin;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"competition_id", "index"}))
@IdClass(CompetitionSubId.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participant extends CreationTimeStampMixin {

  @Id
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "competition_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Competition competition;

  @Id
  private String identifier;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  private Team team;

  @Column(nullable = false)
  private Integer index;

  @JsonIgnore // 직렬화 제외
  @Column(nullable = false)
  private String password;

  private String email;

  @Column(nullable = false, length = 150)
  private String displayedName;

  @Column(nullable = false, length = 150)
  private String hiddenName;

  private String shortIntroduction;

  @PrePersist
  private void initializeIndex() {
    if (competition != null) {
      index = competition.getParticipants().indexOf(this);
    }
  }
}
