package com.compartytion.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compartytion.user.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  boolean existsByEmail(String email);
}
