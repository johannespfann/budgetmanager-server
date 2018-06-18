package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.persistens.model.Run;

import java.time.LocalDateTime;
import java.util.List;

public interface RunProvider {

    List<Run> prepareRuns(LocalDateTime lastRun, LocalDateTime currentRun);

}
