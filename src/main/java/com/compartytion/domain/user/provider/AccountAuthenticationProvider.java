package com.compartytion.domain.user.provider;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.compartytion.domain.user.token.AccountAuthenticationToken;

import static com.compartytion.domain.user.enums.AuthExceptions.NOT_MATCHED_PASSWORD;


@Component
@RequiredArgsConstructor
public class AccountAuthenticationProvider implements AuthenticationProvider {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;


  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String email = authentication.getName();
    String password = authentication.getCredentials().toString();

    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new BadCredentialsException(NOT_MATCHED_PASSWORD.getMessage());
    }

    return new AccountAuthenticationToken(userDetails, password, userDetails.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(AccountAuthenticationToken.class);
  }
}
