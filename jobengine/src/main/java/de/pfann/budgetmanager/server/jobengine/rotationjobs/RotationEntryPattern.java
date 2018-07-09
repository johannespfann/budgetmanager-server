package de.pfann.budgetmanager.server.jobengine.rotationjobs;


import de.pfann.budgetmanager.server.common.model.StandingOrder;

import java.time.LocalDateTime;

public interface RotationEntryPattern {

    boolean isValidPattern(StandingOrder aEntry);

    boolean isExecutable(LocalDateTime aToday, StandingOrder aEntry);

    LocalDateTime getExecutionDate(LocalDateTime aStartTime, LocalDateTime aTimeOfCurrentRun);

}
