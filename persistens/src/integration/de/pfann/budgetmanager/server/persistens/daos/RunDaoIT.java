package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.persistens.core.SessionDistributor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class RunDaoIT {

    /**
     * attributes
     */

    private Run run;

    private Run youngesRun;

    private Run middleOldRun;

    private Run oldesRun;


    /**
     * class under test
     */
    private RunDao runDao;


    @Before
    public void setUp() {
        // Setup db befor each test
        SessionDistributor.createForIT();

        runDao = RunDao.create();
    }

    @Test
    public void testSaveRun(){
        // prepare
        run = new Run();
        runDao.save(run);

        // execute
        List<Run> runs = runDao.getAll();

        // validate
        Assert.assertEquals(run.getExecuted_at().toLocalDate().toEpochDay(),runs.get(0).getExecuted_at().toLocalDate().toEpochDay());
    }

    @Test
    public void testGetYoungesRun(){
        // prepare
        LocalDateTime origin = LocalDateTime.now();
        LocalDateTime youngest = origin.minusDays(1);
        LocalDateTime oldest = origin.plusDays(1);

        youngesRun = new Run(youngest);
        middleOldRun = new Run(origin);
        oldesRun = new Run(oldest);

        runDao.save(middleOldRun);
        runDao.save(youngesRun);
        runDao.save(oldesRun);


        // execute
        Run result = runDao.getYoungesRun();


        for(Run run : runDao.getAll()){
            System.out.println(run.getExecuted_at());
        }

        System.out.println("last run: " + result.getExecuted_at());

        // validate
        Assert.assertEquals(oldest.toLocalDate().toEpochDay(),result.getExecuted_at().toLocalDate().toEpochDay());

    }

    @After
    public void cleanUp(){
        // close db after each test
        SessionDistributor.closeSessions();
    }
}
