package de.pfann.budgetmanager.server.core.jobengine;

import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.daos.RunFacade;
import de.pfann.budgetmanager.server.persistens.model.Run;
import de.pfann.budgetmanager.server.persistens.model.RunInfo;
import sun.rmi.runtime.Log;

import java.util.List;

public class JobEngine {

    private RunFacade runFacade;
    private RunProvider runProvider;

    private List<Job> jobs;


    public JobEngine(RunFacade aRunFacade, RunProvider aRunProvider, List<Job> aJobs){
        runFacade = aRunFacade;
        jobs = aJobs;
        runProvider = aRunProvider;
    }

    public void start(){

        if(RunUtil.isFirstStart(runFacade)){
            LogUtil.info(this.getClass(),"dont found any runs -> first run of application!");
            startFirstTime();
            return;
        }

        Run lastRun = runFacade.getLastRun();
        Run currentRun = new Run();


        List<Run> runs = runProvider.prepareRuns(lastRun.getExecuted_at(),currentRun.getExecuted_at());

        for(Run run : runs){

            for(Job job : jobs){

                executeJob(run,job);

            }

        }

    }

    private void startFirstTime(){
        Run run = new Run();

        for(Job job: jobs){

            executeJob(run, job);

        }

    }


    private void executeJob(Run aRun, Job aJob){
        LogUtil.info(this.getClass(),"execute jobs with run " + aRun.getExecuted_at());
        RunInfo runInfo;
        runInfo = JobExecutor.execute(aRun,aJob);
        LogUtil.info(this.getClass(),"finished jobs and persist run " + aRun.getExecuted_at());
        runFacade.persist(aRun);
        runFacade.persist(runInfo);
    }

}
