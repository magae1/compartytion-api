package com.compartytion.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.compartytion.domain.model.entity.Participant;
import com.compartytion.domain.model.entity.key.CompetitionSubId;


public interface ParticipantRepository extends JpaRepository<Participant, CompetitionSubId> {

}
