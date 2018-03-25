package de.pfann.budgetmanager.server.rotationjobs;

import de.pfann.budgetmanager.server.model.RotationEntry;

import java.util.Date;

public interface RotationEntryPattern {

    boolean isValidPattern(RotationEntry aEntry);

    boolean isExecutable(Date aDate, RotationEntry aEntry);

}
