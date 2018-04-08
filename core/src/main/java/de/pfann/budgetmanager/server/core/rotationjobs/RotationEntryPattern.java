package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.persistens.model.RotationEntry;

import java.time.LocalDate;

public interface RotationEntryPattern {

    boolean isValidPattern(RotationEntry aEntry);

    boolean isExecutable(LocalDate aToday, RotationEntry aEntry);

}
