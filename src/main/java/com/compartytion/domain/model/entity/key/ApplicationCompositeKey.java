package com.compartytion.domain.model.entity.key;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationCompositeKey implements Serializable {

  private String identifier;

  private Long competition;

}
