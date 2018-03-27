package de.pfann.budgetmanager.server.rotationjobs;

import de.pfann.budgetmanager.server.persistens.core.SessionDistributor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
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
        Assert.assertEquals(run.getExecuted_at().toEpochDay(),runs.get(0).getExecuted_at().toEpochDay());
    }

    @Test
    public void testGetYoungesRun(){
        // prepare
        LocalDate origin = LocalDate.now();
        LocalDate youngest = origin.minusDays(1);
        LocalDate oldest = origin.plusDays(1);

        youngesRun = new Run(youngest);
        middleOldRun = new Run(origin);
        oldesRun = new Run(oldest);

        runDao.save(youngesRun);
        runDao.save(middleOldRun);
        runDao.save(oldesRun);

        // execute
        List<Run> result = runDao.getYoungesRun();

        // validate
        Assert.assertEquals(youngest.toEpochDay(),result.get(0).getExecuted_at().toEpochDay());

    }

    @After
    public void cleanUp(){
        // close db after each test
        SessionDistributor.closeSessions();
    }
}
