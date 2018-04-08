package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.persistens.model.Run;

public interface Job {

    String getIdentifier();

    void preExecution(Run aRun);

    void execute(Run aRun);

    void postExecution(Run aRun);

}
