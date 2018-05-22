package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.persistens.model.RotationEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface RotationEntryPattern {

    boolean isValidPattern(RotationEntry aEntry);

    boolean isExecutable(LocalDateTime aToday, RotationEntry aEntry);

}
