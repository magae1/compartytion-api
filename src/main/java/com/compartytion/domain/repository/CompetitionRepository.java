package com.compartytion.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compartytion.domain.model.entity.Competition;


@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

}
