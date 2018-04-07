package de.pfann.budgetmanager.server.core.rotationjobs;

public interface Job {

    String getIdentifier();

    void preExecution(Run aRun);

    void execute(Run aRun);

    void postExecution(Run aRun);

}
