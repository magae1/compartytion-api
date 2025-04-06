package com.compartytion.global.exception;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class InvalidFormException extends RuntimeException {

  private final Map<String, List<String>> messageMap;

  private final HttpStatus status = HttpStatus.BAD_REQUEST;

  public InvalidFormException(Map<String, List<String>> messageMap) {
    this.messageMap = messageMap;
  }

  public InvalidFormException(String attributeName, List<String> messageList) {
    this.messageMap = Map.of(attributeName, messageList);
  }

  public InvalidFormException(String attributeName, String message) {
    this.messageMap = Map.of(attributeName, List.of(message));
  }

}
