package com.compartytion.global.handler;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


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

    Map<String, Object> message = e.getBindingResult().getFieldErrors()
        .stream()
        .collect(Collectors.toMap(FieldError::getField,
            (fieldError) ->
                Objects.requireNonNull(fieldError.getDefaultMessage())
        ));
    body.put("message", message);

    return new ResponseEntity<>(body, e.getStatusCode());
  }

}
