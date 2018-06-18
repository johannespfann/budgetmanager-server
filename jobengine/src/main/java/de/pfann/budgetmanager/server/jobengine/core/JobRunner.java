package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.persistens.model.Run;

public class JobRunner {

    private Job job;

    public JobRunner(Job aJob){
        job = aJob;
    }

    public void runJob(Run aRun) throws JobException {

        job.preExecution(aRun);
        job.execute(aRun);
        job.postExecution(aRun);

    }

    public Job getJob(){
        return job;
    }

}
