package com.compartytion.domain.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.model.entity.Competition.Status;
import com.compartytion.domain.repository.projection.CompetitionStatusAndCreatorId;


@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

  Optional<CompetitionStatusAndCreatorId> findCompetitionStatusAndCreatorIdById(Long id);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query(value = "DELETE FROM Competition c WHERE c.id = :competitionId")
  void deleteByCompetitionId(Long competitionId);

  @Modifying
  @Query(value = "UPDATE Competition c SET c.status = :status WHERE c.id = :id")
  void updateStatusById(Long id, Status status);
}
