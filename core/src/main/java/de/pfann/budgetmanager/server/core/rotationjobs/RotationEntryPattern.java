package de.pfann.budgetmanager.server.core.rotationjobs;

import java.time.LocalDate;

public interface RotationEntryPattern {

    boolean isValidPattern(RotationEntry aEntry);

    boolean isExecutable(LocalDate aToday, RotationEntry aEntry);

}
