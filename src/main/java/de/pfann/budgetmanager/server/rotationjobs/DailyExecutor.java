package de.pfann.budgetmanager.server.rotationjobs;

import java.util.Timer;

public class DailyExecutor {


    public DailyExecutor(){
        // default
    }


    public void start(){
        RotationEntryJob rotationEntryJob = new RotationEntryJob();

        RunInfoDao runInfoDao = RunInfoDao.create();
        RunDao runDao = RunDao.create();
        RunFacade runFacade = new RunFacade(runInfoDao,runDao);

        JobExecuterEngine engine = JobExecuterEngine.builder()
                .addJob(rotationEntryJob)
                .withRunFacade(runFacade)
                .build();

        Timer timer = new Timer();

        timer.schedule(engine,1000,1000 * 60 *60 *24 );


    }
}
