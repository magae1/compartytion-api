package com.compartytion.global.filter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
@RequiredArgsConstructor
public class ExceptionHandleFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      filterChain.doFilter(request, response);
    } catch (ConstraintViolationException e) {
      Map<String, Object> body = new LinkedHashMap<>(Map.of(
          "code", HttpStatus.BAD_REQUEST.value()
      ));

      Map<String, Object> message = e.getConstraintViolations()
          .stream()
          .collect(Collectors.toMap((violation) -> violation.getPropertyPath().toString(),
              ConstraintViolation::getMessageTemplate));
      body.put("message", message);
      setErrorResponse(response, HttpStatus.BAD_REQUEST, body);
    } catch (Exception e) {
      setErrorResponse(response, HttpStatus.UNAUTHORIZED, e.getMessage());
    }
  }

  private void setErrorResponse(HttpServletResponse response, HttpStatus status, Object body)
      throws IOException {
    response.setStatus(status.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    objectMapper.writeValue(response.getWriter(), body);
  }
}
