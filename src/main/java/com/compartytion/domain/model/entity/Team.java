package com.compartytion.domain.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

import com.compartytion.domain.model.mixin.CreationTimeStampMixin;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"competition_id", "index"}))
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends CreationTimeStampMixin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "competition_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Competition competition;

  @Column(nullable = false, length = 150)
  private String name;

  @Column(nullable = false)
  private Integer index;

  @PrePersist
  private void initializeIndex() {
    if (competition != null) {
      index = competition.getTeams().indexOf(this);
    }
  }
}
