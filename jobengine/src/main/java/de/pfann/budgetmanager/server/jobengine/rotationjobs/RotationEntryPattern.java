package de.pfann.budgetmanager.server.jobengine.rotationjobs;

import de.pfann.budgetmanager.server.persistens.model.RotationEntry;

import java.time.LocalDateTime;

public interface RotationEntryPattern {

    boolean isValidPattern(RotationEntry aEntry);

    boolean isExecutable(LocalDateTime aToday, RotationEntry aEntry);

    LocalDateTime getExecutionDate(LocalDateTime aStartTime, LocalDateTime aTimeOfCurrentRun);

}
