package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.common.model.RunInfo;

import java.util.List;

public interface RunFacade {
    void persist(Run aRun);

    void persist(RunInfo aRunInfo);

    Run getLastRun();

    List<Run> getAllRuns();
}
