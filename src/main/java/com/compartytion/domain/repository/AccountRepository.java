package com.compartytion.domain.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.repository.projection.DetailAccountInfo;
import com.compartytion.domain.repository.projection.SimpleAccountInfo;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  boolean existsByEmail(String email);

  Optional<Account> findByEmail(String email);

  Optional<SimpleAccountInfo> findSimpleAccountInfoById(Long id);

  Optional<SimpleAccountInfo> findSimpleAccountInfoByEmail(String email);

  Optional<DetailAccountInfo> findDetailAccountInfoById(Long id);
}
