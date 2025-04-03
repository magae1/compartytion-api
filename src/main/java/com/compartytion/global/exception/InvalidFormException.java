package com.compartytion.global.exception;

import java.util.List;
import java.util.Map;

import lombok.Getter;


@Getter
public class InvalidFormException extends RuntimeException {

  private Map<String, List<String>> messageMap;

  public InvalidFormException(Map<String, List<String>> messageMap) {
    this.messageMap = messageMap;
  }

}
