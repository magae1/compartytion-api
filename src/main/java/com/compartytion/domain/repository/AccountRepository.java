package com.compartytion.domain.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compartytion.domain.model.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  boolean existsByEmail(String email);

  Optional<Account> findByEmail(String email);
}
