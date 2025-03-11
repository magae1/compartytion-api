package com.compartytion.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@IdClass(CompetitionSubId.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends CreationTimeStampMixin {

  @Id
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "competition_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Competition competition;

  @Id
  private String identifier;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  @JsonIgnore // 직렬화 제외
  private String password;

  private String email;

  @Column(nullable = false, length = 150)
  private String displayedName;

  @Column(nullable = false, length = 150)
  private String hiddenName;

  private String shortIntroduction;
}
