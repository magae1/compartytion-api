package com.compartytion.domain.model.mixin;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.UpdateTimestamp;


@Getter
@MappedSuperclass
public class TimeStampMixin extends CreationTimeStampMixin {

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

}
