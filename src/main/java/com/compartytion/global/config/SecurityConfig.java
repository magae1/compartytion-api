package com.compartytion.global.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

import com.compartytion.domain.user.filter.AccountAuthenticationFilter;
import com.compartytion.domain.user.handler.AccountAuthenticationFailureHandler;
import com.compartytion.domain.user.handler.AccountAuthenticationSuccessHandler;
import com.compartytion.global.filter.ExceptionHandleFilter;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final AccountAuthenticationSuccessHandler accountAuthenticationSuccessHandler;
  private final AccountAuthenticationFailureHandler accountAuthenticationFailureHandler;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final ExceptionHandleFilter exceptionHandleFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .addFilterBefore(exceptionHandleFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(accountAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .httpBasic(HttpBasicConfigurer::disable)
        .csrf(CsrfConfigurer::disable)
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/error/**").permitAll()
            .anyRequest().authenticated());
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AccountAuthenticationFilter accountAuthenticationFilter() throws Exception {
    AccountAuthenticationFilter filter = new AccountAuthenticationFilter();
    filter.setAuthenticationManager(authenticationManager());
    filter.setAuthenticationSuccessHandler(accountAuthenticationSuccessHandler);
    filter.setAuthenticationFailureHandler(accountAuthenticationFailureHandler);
    filter.setSecurityContextRepository(
        new DelegatingSecurityContextRepository(
            new RequestAttributeSecurityContextRepository(),
            new HttpSessionSecurityContextRepository()
        ));
    return filter;
  }

}
