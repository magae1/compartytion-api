package com.compartytion.domain.user.dto;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.compartytion.domain.model.entity.Account;


@ToString(exclude = "password")
public class AccountDetails implements UserDetails {

  @Getter
  private final Long id;

  private final String email;

  @JsonIgnore
  private final String password;


  public AccountDetails(Account account) {
    this.id = account.getId();
    this.email = account.getEmail();
    this.password = account.getPassword();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }
}
