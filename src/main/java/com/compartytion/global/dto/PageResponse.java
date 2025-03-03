package com.compartytion.global.dto;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Getter
@ToString
public class PageResponse<E> {

  private final long totalCount;
  private long page = 0;
  private int size = 0;
  private final List<E> results;

  public <ENTITY> PageResponse(Page<ENTITY> page, Function<? super ENTITY, E> mapper) {
    results = page.stream().map(mapper).collect(Collectors.toList());
    totalCount = page.getTotalElements();
    init(page.getPageable());
  }

  private void init(Pageable pageable) {
    page = pageable.getPageNumber() + 1;
    size = pageable.getPageSize();
  }
}
