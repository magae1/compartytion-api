package com.compartytion.domain.repository.projection;


public record CompetitionPermissions(
    Boolean exists,
    Boolean manager,
    Boolean participant
) {

}
