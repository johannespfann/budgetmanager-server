package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.common.model.RunInfo;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.daos.RunSQLFacade;

import java.util.List;

public class JobEngine {

    private static final String JOB_STATUS_SUCCESS = "SUCCESS";
    private static final String JOB_STATUS_FAILD = "FAILD";

    private RunSQLFacade runFacade;
    private RunProvider runProvider;

    private List<JobRunner> jobRunners;

    public JobEngine(RunSQLFacade aRunFacade, RunProvider aRunProvider, List<JobRunner> aJobRunners) {
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
            LogUtil.info(this.getClass(),"finished run and stop runinfo");
            runInfo.stop(JOB_STATUS_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.info(this.getClass(),e.getMessage());
            runInfo.stop(JOB_STATUS_FAILD);
            LogUtil.error(this.getClass(), "failed job: " + aJobRunner.getJob().getIdentifier());
        } finally {
            LogUtil.info(this.getClass(), "finished jobRunners and persist run " + aRun.getExecuted_at());
            LogUtil.info(this.getClass(),"RunInfo Start : " + runInfo.getStart_at());
            LogUtil.info(this.getClass(),"RunInfo End   : " + runInfo.getEnd_at());
            LogUtil.info(this.getClass(),"RunInfo Ident : " + runInfo.getIdentifier());
            runFacade.persist(aRun);
            runFacade.persist(runInfo);
        }
    }

}