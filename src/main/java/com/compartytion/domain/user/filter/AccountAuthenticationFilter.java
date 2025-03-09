package com.compartytion.domain.user.filter;


import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.compartytion.domain.user.dto.request.LoginRequest;
import com.compartytion.domain.user.token.AccountAuthenticationToken;


public class AccountAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  public AccountAuthenticationFilter() {
    super(new AntPathRequestMatcher("/auth/login", "POST"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response)
      throws RuntimeException, IOException {

    LoginRequest loginRequest = objectMapper.readValue(request.getReader(),
        LoginRequest.class);
    Set<ConstraintViolation<LoginRequest>> validateResultSet = validator.validate(loginRequest);

    if (!validateResultSet.isEmpty()) {
      throw new ConstraintViolationException(validateResultSet);
    }

    AccountAuthenticationToken token = new AccountAuthenticationToken(
        loginRequest.email(), loginRequest.password());

    return getAuthenticationManager().authenticate(token);
  }
}
