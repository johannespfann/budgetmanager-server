package de.pfann.budgetmanager.server.core.jobengine;

import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.daos.RunFacade;
import de.pfann.budgetmanager.server.persistens.model.Run;
import de.pfann.budgetmanager.server.persistens.model.RunInfo;

import java.time.temporal.ChronoUnit;
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
            LogUtil.info(this.getClass(),"dont found any runs -> first run of application!");
            startFirstTime();
            return;
        }

        Run lastRun = runFacade.getLastRun();
        Run currentRun = new Run();

        List<Run> runs = runProvider.prepareRuns(lastRun.getExecuted_at(),currentRun.getExecuted_at());

        for(Run run : runs){

            for (JobRunner job : jobRunners) {

                executeJob(run,job);

            }
        }
    }

    private void startFirstTime(){
        Run run = new Run();

        for (JobRunner job : jobRunners) {
            executeJob(run, job);
        }
    }

    private void executeJob(Run aRun, JobRunner aJobRunner) {
        LogUtil.info(this.getClass(), "execute jobRunners with run " + aRun.getExecuted_at());

        RunInfo runInfo = new RunInfo(aRun, aJobRunner.getJob().getIdentifier());
        runInfo.start();

        try {

            aJobRunner.runJob(aRun);

            runInfo.stop(JOB_STATUS_SUCCESS);
            long durationInMilliSeconds = ChronoUnit.MILLIS.between(runInfo.getStart_at(), runInfo.getEnd_at());
            LogUtil.info(this.getClass(), "finished job: " + aJobRunner.getJob().getIdentifier() + " in " + durationInMilliSeconds + " ms");

        } catch (JobException e) {
            runInfo.stop(JOB_STATUS_FAILD);
            LogUtil.error(this.getClass(), "failed job: " + aJobRunner.getJob().getIdentifier());
        } finally {
            LogUtil.info(this.getClass(), "finished jobRunners and persist run " + aRun.getExecuted_at());
            runFacade.persist(aRun);
            runFacade.persist(runInfo);
        }
    }

}
