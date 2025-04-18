package com.compartytion.global.handler;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import com.compartytion.global.exception.InvalidFormException;


@Hidden
@RestControllerAdvice
public class RestControllerExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e) {
    Map<String, Object> body = new LinkedHashMap<>(Map.of(
        "code", e.getStatusCode().value()
    ));
    if (Objects.nonNull(e.getReason())) {
      body.put("detail", e.getReason());
    }

    return new ResponseEntity<>(body, e.getStatusCode());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {

    Map<String, Object> body = new LinkedHashMap<>(Map.of(
        "code", e.getStatusCode().value()
    ));

    Map<String, List<String>> message = new HashMap<>();
    for (FieldError error : e.getBindingResult().getFieldErrors()) {
      message.computeIfAbsent(error.getField(), key -> new ArrayList<>())
          .add(error.getDefaultMessage());
    }
    body.put("message", message);

    return new ResponseEntity<>(body, e.getStatusCode());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<?> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    Map<String, Object> body = new LinkedHashMap<>(Map.of(
        "code", HttpStatus.BAD_REQUEST.value(),
        "detail", e.getMessage()
    ));

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidFormException.class)
  public ResponseEntity<?> handleInvalidFormException(InvalidFormException e) {
    Map<String, Object> body = new LinkedHashMap<>(Map.of(
        "code", e.getStatus().value()
    ));

    body.put("message", e.getMessageMap());

    return new ResponseEntity<>(body, e.getStatus());
  }

}
