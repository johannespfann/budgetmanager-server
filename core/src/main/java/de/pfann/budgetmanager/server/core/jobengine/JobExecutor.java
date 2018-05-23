package de.pfann.budgetmanager.server.core.jobengine;

import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.model.Run;
import de.pfann.budgetmanager.server.persistens.model.RunInfo;

import java.time.temporal.ChronoUnit;

public class JobExecutor {

    private static final String JOB_STATUS_SUCCESS = "SUCCESS";
    private static final String JOB_STATUS_FAILD = "FAILD";

    public static RunInfo execute(Run aRun, Job aJob) {

        LogUtil.info(JobExecutor.class, "invoke job  : " + aJob.getIdentifier() + " for run at " + aRun.getExecuted_at());

        RunInfo runInfo = new RunInfo(aRun,aJob.getIdentifier());
        runInfo.start();

        try {
            aJob.preExecution(aRun);
            aJob.execute(aRun);
            aJob.postExecution(aRun);

            runInfo.stop(JOB_STATUS_SUCCESS);
            long durationInMilliSeconds = ChronoUnit.MILLIS.between(runInfo.getStart_at(), runInfo.getEnd_at());
            LogUtil.info(JobExecutor.class, "finished job: " + aJob.getIdentifier() + " in " + durationInMilliSeconds + " ms");

        }catch (JobException e){
            runInfo.stop(JOB_STATUS_FAILD);
            LogUtil.error(JobExecutor.class, "failed job: " + aJob.getIdentifier());
        }finally {
            return runInfo;
        }
    }
}
