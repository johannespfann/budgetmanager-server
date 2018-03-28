package de.pfann.budgetmanager.server.rotationjobs;

import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.EntryFacade;
import de.pfann.budgetmanager.server.persistens.daos.RotationEntryFacade;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

public class DailyExecutor {


    public DailyExecutor(){
        // default
    }


    public void start(){


        RotationEntryPattern monthlyRotationEntry = new MonthlyRotationEntry();

        List<RotationEntryPattern> patternList = new LinkedList<>();
        patternList.add(monthlyRotationEntry);

        Job rotationEntryJob = new RotationEntryJob(
                patternList,
                new AppUserFacade(),
                new EntryFacade(),
                new RotationEntryFacade());

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
