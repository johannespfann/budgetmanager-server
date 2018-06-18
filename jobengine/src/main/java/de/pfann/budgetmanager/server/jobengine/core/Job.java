package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.persistens.model.Run;

public interface Job {

    String getIdentifier();

    void preExecution(Run aRun) throws JobException;

    void execute(Run aRun) throws JobException;

    void postExecution(Run aRun) throws JobException;

}
