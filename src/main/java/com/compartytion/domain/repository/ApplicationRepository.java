package com.compartytion.domain.repository;


import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.compartytion.domain.model.entity.Application;
import com.compartytion.domain.model.entity.key.CompetitionSubId;


@Repository
public interface ApplicationRepository extends JpaRepository<Application, CompetitionSubId> {

  @Query(value = "SELECT p.identifier "
      + "FROM Participant p "
      + "WHERE p.competition.id = :competitionId "
      + "UNION "
      + "SELECT a.identifier "
      + "FROM Application a "
      + "WHERE a.competition.id = :competitionId")
  Set<String> findAllIdentifierInParticipantsAndApplicants(Long competitionId);
}
