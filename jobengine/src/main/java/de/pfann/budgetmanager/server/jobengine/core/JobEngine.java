package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.common.facade.RunFacade;
import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.common.model.RunInfo;
import de.pfann.server.logging.core.RunLog;


import java.time.LocalDateTime;
import java.util.List;

public class JobEngine {

    private static final String JOB_STATUS_SUCCESS = "SUCCESS";
    private static final String JOB_STATUS_FAILD = "FAILD";

    private RunFacade runFacade;
    private RunProvider runProvider;

    private List<JobRunner> jobRunners;

    public JobEngine(RunFacade aRunFacade, RunProvider aRunProvider, List<JobRunner> aJobRunners) {
        runFacade = aRunFacade;
        jobRunners = aJobRunners;
        runProvider = aRunProvider;
    }

    public void start(){

        if(RunUtil.isFirstStart(runFacade)){
            RunLog.info(this.getClass(),"dont found any runs -> first run of application!");
            startFirstTime();
            return;
        }

        Run lastRun = runFacade.getLastRun();
        RunLog.info(this.getClass(),"Found last run: " + lastRun.getExecuted_at());
        List<Run> runs = runProvider.prepareRuns(lastRun.getExecuted_at(), LocalDateTime.now());
        RunLog.info(this.getClass(),"Calculated " + runs.size() + " runs!");

        for(Run run : runs){

            RunLog.info(this.getClass(), "Persist new run : " + run.getExecuted_at());
            runFacade.persist(run);

            for (JobRunner job : jobRunners) {

                executeJob(run,job);

            }
        }
    }

    private void startFirstTime(){
        Run run = new Run();
        RunLog.info(this.getClass(), "Persist new run : " + run.getExecuted_at());
        runFacade.persist(run);

        for (JobRunner job : jobRunners) {
            executeJob(run, job);
        }
    }

    private void executeJob(Run aRun, JobRunner aJobRunner) {
        RunLog.info(this.getClass(), "execute jobRunners with run " + aRun.getExecuted_at());

        RunInfo runInfo = new RunInfo(aRun, aJobRunner.getJob().getIdentifier());
        runInfo.start();

        try {

            aJobRunner.runJob(aRun);
            RunLog.info(this.getClass(),"finished run and stop runinfo");
            runInfo.stop(JOB_STATUS_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            RunLog.info(this.getClass(),e.getMessage());
            runInfo.stop(JOB_STATUS_FAILD);
            RunLog.error(this.getClass(), "failed job: " + aJobRunner.getJob().getIdentifier());
        } finally {

            RunLog.info(this.getClass(), "finished jobRunners and persist run " + aRun.getExecuted_at());
            RunLog.info(this.getClass(),"RunInfo Start : " + runInfo.getStart_at());
            RunLog.info(this.getClass(),"RunInfo End   : " + runInfo.getEnd_at());
            RunLog.info(this.getClass(),"RunInfo Ident : " + runInfo.getIdentifier());

            runFacade.persist(runInfo);
        }

    }

}
