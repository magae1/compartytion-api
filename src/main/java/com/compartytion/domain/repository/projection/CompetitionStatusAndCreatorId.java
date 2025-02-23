package com.compartytion.domain.repository.projection;


import com.compartytion.domain.model.entity.Competition.Status;

public record CompetitionStatusAndCreatorId(
    Long id,
    Long creatorId,
    Status status
) {

}
