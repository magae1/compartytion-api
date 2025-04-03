package com.compartytion.domain.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.compartytion.domain.model.entity.Participant;
import com.compartytion.domain.model.entity.key.ApplicationCompositeKey;


public interface ParticipantRepository extends JpaRepository<Participant, ApplicationCompositeKey> {

  @Query(value = "SELECT COUNT(p) > 0 "
      + "FROM Participant p "
      + "WHERE p.competition.id = :competitionId AND p.account.id = :accountId"
  )
  boolean existsByCompetitionIdAndAccountId(Long competitionId, Long accountId);

  @Query(value = "SELECT p.identifier "
      + "FROM Participant p "
      + "WHERE p.competition.id = :competitionId")
  Set<String> findAllIdentifier(Long competitionId);
}
