package com.compartytion.domain.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.compartytion.domain.model.entity.Competition;
import com.compartytion.domain.repository.projection.CompetitionStatusAndCreatorId;
import com.compartytion.domain.repository.projection.IdAndTitleOnly;


@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

  Optional<CompetitionStatusAndCreatorId> findCompetitionStatusAndCreatorIdById(Long id);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query(value = "DELETE FROM Competition c WHERE c.id = :competitionId")
  void deleteByCompetitionId(Long competitionId);

  @Modifying
  @Query(value = "UPDATE Competition c SET c.status = :status WHERE c.id = :id")
  void updateStatusById(Long id, Competition.Status status);

  @Query(value = "SELECT c.id AS id, "
      + "c.title AS title "
      + "FROM Competition c "
      + "WHERE c.status <> com.compartytion.domain.model.entity.Competition.Status.DONE AND (c.creator.id = :accountId "
      + "OR EXISTS (SELECT 1 FROM Participant p WHERE p.competition.id = c.id AND p.account.id = :accountId))")
  Page<IdAndTitleOnly> findJoinedCompetitionsByAccountId(Long accountId, Pageable pageable);
}
