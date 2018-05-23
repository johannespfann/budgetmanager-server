package de.pfann.budgetmanager.server.core.jobengine;

import de.pfann.budgetmanager.server.persistens.model.Run;
import jdk.nashorn.internal.scripts.JO;

public interface Job {

    String getIdentifier();

    void preExecution(Run aRun) throws JobException;

    void execute(Run aRun) throws JobException;

    void postExecution(Run aRun) throws JobException;

}
